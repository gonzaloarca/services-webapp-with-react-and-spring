package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.persistence.utils.PagingUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JobContractDaoJpa implements JobContractDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public JobContractWithImage create(long clientId, long packageId, String description, LocalDateTime scheduledDate) {
        return create(clientId, packageId, description, scheduledDate, null);
    }

    @Override
    public JobContractWithImage create(long clientId, long packageId, String description, LocalDateTime scheduledDate, ByteImage image) {
        User client = em.find(User.class, clientId);
        if (client == null)
            throw new UserNotFoundException();

        JobPackage jobPackage = em.find(JobPackage.class, packageId);
        if (jobPackage == null)
            throw new JobPackageNotFoundException();

        JobContractWithImage jobContractWithImage = new JobContractWithImage(client, jobPackage, LocalDateTime.now(), scheduledDate, LocalDateTime.now(), description, image);
        em.persist(jobContractWithImage);

        return jobContractWithImage;
    }

    @Override
    public Optional<JobContract> findById(long id) {
        return Optional.ofNullable(em.find(JobContract.class, id));
    }

    @Override
    public List<JobContract> findByClientId(long id, List<JobContract.ContractState> states, int page) {
        if (states == null)
            throw new IllegalArgumentException();

        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM contract WHERE client_id = :id " +
                "AND contract_state IN :states ORDER BY contract_creation_date DESC")
                .setParameter("id", id)
                .setParameter("states", states.isEmpty() ? null : states.stream().map(Enum::ordinal)
                        .collect(Collectors.toList()));

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobContract> findByClientIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states,
                                                                       int page) {
        if (states == null)
            throw new IllegalArgumentException();

        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM contract WHERE client_id = :id " +
                "AND contract_state IN :states ORDER BY contract_last_modified_date DESC")
                .setParameter("id", id)
                .setParameter("states", states.isEmpty() ? null : states.stream().map(Enum::ordinal)
                        .collect(Collectors.toList()));

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobContract> findByProId(long id, List<JobContract.ContractState> states, int page) {
        if (states == null)
            throw new IllegalArgumentException();

        Query nativeQuery = em.createNativeQuery(
                "SELECT contract_id FROM contract NATURAL JOIN job_package NATURAL JOIN job_post " +
                        "WHERE user_id = :id AND contract_state IN :states ORDER BY contract_creation_date DESC")
                .setParameter("id", id)
                .setParameter("states", states.isEmpty() ? null : states.stream().map(Enum::ordinal)
                        .collect(Collectors.toList()));

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<JobContract> findByProIdAndSortedByModificationDate(long id, List<JobContract.ContractState> states, int page) {
        if (states == null)
            throw new IllegalArgumentException();

        Query nativeQuery = em.createNativeQuery(
                "SELECT contract_id FROM contract NATURAL JOIN job_package NATURAL JOIN job_post " +
                        "WHERE user_id = :id AND contract_state IN :states ORDER BY contract_last_modified_date DESC")
                .setParameter("id", id)
                .setParameter("states", states.isEmpty() ? null : states.stream().map(Enum::ordinal)
                        .collect(Collectors.toList()));

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
    public int findMaxPageContractsByClientId(long id, List<JobContract.ContractState> states) {
        if (states == null)
            throw new IllegalArgumentException();

        Long size = em.createQuery("SELECT COUNT(*) FROM JobContract jc WHERE jc.client.id = :id AND jc.state IN :states", Long.class)
                .setParameter("id", id).setParameter("states", states.isEmpty() ? null : states).getSingleResult();

        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findMaxPageContractsByProId(long id, List<JobContract.ContractState> states) {
        if (states == null)
            throw new IllegalArgumentException();

        Long size = em.createQuery("SELECT COUNT(*) FROM JobContract jc WHERE jc.jobPackage.jobPost.user.id = :id AND " +
                "jc.state IN :states", Long.class)
                .setParameter("id", id).setParameter("states", states.isEmpty() ? null : states).getSingleResult();

        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }

    private List<JobContract> executePageQuery(int page, Query nativeQuery) {
        List<Long> filteredIds = PagingUtil.getFilteredIds(page, nativeQuery);

        return em.createQuery("FROM JobContract AS jc WHERE jc.id IN :filteredIds",
                JobContract.class).setParameter("filteredIds", filteredIds.isEmpty() ? null : filteredIds).getResultList().stream().sorted(
                //Ordenamos los elementos segun el orden de filteredIds
                Comparator.comparingInt(o -> filteredIds.indexOf(o.getId()))
        ).collect(Collectors.toList());
    }

    @Override
    public void changeContractState(long id, JobContract.ContractState state) {
        JobContract contract = em.find(JobContract.class, id);

        if (contract == null)
            throw new JobContractNotFoundException();

        contract.setState(state);
        contract.setLastModifiedDate(LocalDateTime.now());

        em.persist(contract);
    }

    @Override
    public Optional<JobContractWithImage> findJobContractWithImage(long id) {
        return Optional.ofNullable(em.find(JobContractWithImage.class, id));
    }

    @Override
    public Optional<ByteImage> findImageByContractId(long id) {
        List<ByteImage> resultList = em.createQuery("SELECT contract.byteImage FROM JobContractWithImage contract WHERE contract.id = :id", ByteImage.class).setParameter("id", id).getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.ofNullable(resultList.get(0));

    }

    @Override
    public Optional<JobContract> findByIdWithUser(long id) {
        List<JobContract> contracts = em.createQuery("FROM JobContract as jc JOIN FETCH jc.client JOIN FETCH jc.jobPackage as jp JOIN FETCH jp.jobPost as post JOIN FETCH post.user WHERE jc.id = :id",JobContract.class).setParameter("id",id).getResultList();
        if(contracts.isEmpty()){
            return Optional.empty();
        }
        return Optional.ofNullable(contracts.get(0));
    }
}
