package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.persistence.utils.PagingUtil;
import ar.edu.itba.paw.models.exceptions.JobPostNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

        List<Long> filteredIds = PagingUtil.getFilteredIds(page, nativeQuery);

        return em.createQuery("FROM JobPackage AS jpack WHERE jpack.id IN :filteredIds", JobPackage.class)
                .setParameter("filteredIds", (filteredIds.isEmpty())? null : filteredIds)
                .getResultList().stream().sorted(
                        //Ordenamos los elementos segun el orden de filteredIds
                        Comparator.comparingInt(o -> filteredIds.indexOf(o.getId()))
                ).collect(Collectors.toList());
    }

    @Override
    public Optional<JobPost> findPostByPackageId(long id) {
        return em.createQuery(
                "SELECT post FROM JobPackage package JOIN package.jobPost post WHERE post.id = :id"
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

    @Override
    public int findByPostIdMaxPage(long id) {
        Long size = em.createQuery("SELECT COUNT(*) FROM JobPackage jpackage WHERE jpackage.jobPost.id = :id AND jpackage.isActive = TRUE", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil((double) size.intValue() / HirenetUtils.PAGE_SIZE);
    }
}
