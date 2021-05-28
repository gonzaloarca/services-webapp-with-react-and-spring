package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import ar.edu.itba.paw.persistence.utils.PagingUtil;
import exceptions.JobPackageNotFoundException;
import exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JobContractDaoJpa implements JobContractDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public JobContract create(long clientId, long packageId, String description) {
        return create(clientId, packageId, description, null);
    }

    @Override
    public JobContract create(long clientId, long packageId, String description, ByteImage image) {
        User client = em.find(User.class, clientId);
        if (client == null)
            throw new UserNotFoundException();

        JobPackage jobPackage = em.find(JobPackage.class, packageId);
        if (jobPackage == null)
            throw new JobPackageNotFoundException();

        EncodedImage encodedImage = new EncodedImage(null, null);
        if(image != null)
            encodedImage = new EncodedImage(ImageDataConverter.getEncodedString(image.getData()), image.getType());

        JobContract jobContract = new JobContract(client, jobPackage, LocalDateTime.now(), description, image, encodedImage);
        em.persist(jobContract);

        return jobContract;
    }

    @Override
    public Optional<JobContract> findById(long id) {
        return addEncodedImage(Optional.ofNullable(em.find(JobContract.class, id)));
    }

    @Override
    public List<JobContract> findByClientId(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM contract WHERE client_id = :id ORDER BY contract_creation_date DESC")
                .setParameter("id", id);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobContract> findByProId(long id, int page) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT contract_id FROM contract NATURAL JOIN job_package NATURAL JOIN job_post " +
                        "WHERE user_id = :id ORDER BY contract_creation_date DESC")
                .setParameter("id", id);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobContract> findByPostId(long id, int page) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT contract_id FROM contract NATURAL JOIN job_package " +
                        "WHERE post_id = :id ORDER BY contract_creation_date DESC")
                .setParameter("id", id);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobContract> findByPackageId(long id, int page) {
        Query nativeQuery = em.createNativeQuery(
                "SELECT contract_id FROM contract " +
                        "WHERE package_id = :id ORDER BY contract_creation_date DESC")
                .setParameter("id", id);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Optional<User> findClientByContractId(long id) {
        return em.createQuery(
                "SELECT jc.client FROM JobContract jc WHERE jc.id = :id", User.class
        ).setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findProByContractId(long id) {
        return em.createQuery(
                "SELECT jc.jobPackage.jobPost.user FROM JobContract jc JOIN FETCH jc.jobPackage.jobPost.user WHERE jc.id = :id", User.class
        ).setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public int findContractsQuantityByProId(long id) {
        return em.createQuery("SELECT COUNT(*) FROM JobContract jc WHERE jc.jobPackage.jobPost.user.id = :id", Long.class)
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0L).intValue();
    }

    @Override
    public int findContractsQuantityByPostId(long id) {
        return em.createQuery("SELECT COUNT(*) FROM JobContract jc WHERE jc.jobPackage.jobPost.id = :id", Long.class)
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0L).intValue();
    }

    @Override
    public int findMaxPageContractsByClientId(long id) {
        Long size = em.createQuery("SELECT COUNT(*) FROM JobContract jc WHERE jc.client.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();

        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageContractsByProId(long id) {
        Long size = em.createQuery("SELECT COUNT(*) FROM JobContract jc WHERE jc.jobPackage.jobPost.user.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    private List<JobContract> executePageQuery(int page, Query nativeQuery) {
        List<Long> filteredIds = PagingUtil.getFilteredIds(page, nativeQuery);

        return addEncodedImage(em.createQuery("FROM JobContract AS jc WHERE jc.id IN :filteredIds",
                JobContract.class).setParameter("filteredIds", filteredIds).getResultList().stream().sorted(
                //Ordenamos los elementos segun el orden de filteredIds
                Comparator.comparingInt(o -> filteredIds.indexOf(o.getId()))
        ).collect(Collectors.toList()));
    }

    private Optional<JobContract> addEncodedImage(Optional<JobContract> maybeContract) {
        if(maybeContract.isPresent()) {
            ByteImage byteImage = maybeContract.get().getImage();
            EncodedImage encodedImage = new EncodedImage(null, null);
            if(byteImage != null)
                encodedImage = new EncodedImage(ImageDataConverter.getEncodedString(byteImage.getData()), byteImage.getType());
            maybeContract.get().setEncodedImage(encodedImage);
        }
        return maybeContract;
    }

    private List<JobContract> addEncodedImage(List<JobContract> jobContractList) {
        for(JobContract jobContract : jobContractList) {
            ByteImage byteImage = jobContract.getImage();
            EncodedImage encodedImage = new EncodedImage(null, null);
            if(byteImage != null)
                encodedImage = new EncodedImage(ImageDataConverter.getEncodedString(byteImage.getData()), byteImage.getType());
            jobContract.setEncodedImage(encodedImage);
        }
        return jobContractList;
    }
}
