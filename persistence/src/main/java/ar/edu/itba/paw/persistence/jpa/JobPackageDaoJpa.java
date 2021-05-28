package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import exceptions.JobPostNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JobPackageDaoJpa implements JobPackageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public JobPackage create(long postId, String title, String description, Double price, JobPackage.RateType rateType) {
        JobPost jobPost = em.find(JobPost.class, postId);
        if(jobPost == null)
            throw new JobPostNotFoundException();

        JobPackage jobPackage = new JobPackage(jobPost, title, description, price, rateType);
        em.persist(jobPackage);
        return jobPackage;
    }

    @Override
    public Optional<JobPackage> findById(long id) {
        return Optional.ofNullable(em.find(JobPackage.class, id));
    }

    @Override
    public List<JobPackage> findByPostId(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT package_id FROM job_package WHERE post_id = :id")
                .setParameter("id", id);

        if (page != HirenetUtils.ALL_PAGES) {
            nativeQuery.setFirstResult((page) * HirenetUtils.PAGE_SIZE);
            nativeQuery.setMaxResults(HirenetUtils.PAGE_SIZE);
        }

        @SuppressWarnings("unchecked")
        List<Long> filteredIds = (List<Long>) nativeQuery.getResultList().stream()
                .map(e -> Long.valueOf(e.toString())).collect(Collectors.toList());

        if(filteredIds.isEmpty())
            return new ArrayList<>();

        return em.createQuery("FROM JobPackage AS jpack WHERE jpack.id IN :filteredIds", JobPackage.class)
                .setParameter("filteredIds", filteredIds).getResultList().stream().sorted(
                        //Ordenamos los elementos segun el orden de filteredIds
                        Comparator.comparingInt(o -> filteredIds.indexOf(o.getId()))
                ).collect(Collectors.toList());
    }

    @Override
    public Optional<JobPost> findPostByPackageId(long id) {
        return em.createQuery(
                "SELECT jpack.jobPost FROM JobPackage jpack JOIN FETCH jpack.jobPost WHERE jpack.id = :id"
                , JobPost.class).setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public boolean deletePackage(long id) {
        JobPackage jobPack = em.find(JobPackage.class, id);
        if (jobPack != null) {
            jobPack.setActive(false);
            em.persist(jobPack);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePackage(long id, String title, String description, Double price, JobPackage.RateType rateType) {
        JobPackage jobPack = em.find(JobPackage.class, id);
        if (jobPack != null) {
            jobPack.setTitle(title);
            jobPack.setDescription(description);
            jobPack.setPrice(price);
            jobPack.setRateType(rateType);
            em.persist(jobPack);
            return true;
        }
        return false;
    }
}
