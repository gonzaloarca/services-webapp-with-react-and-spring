package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JobPackageDaoJpa implements JobPackageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public JobPackage create(long postId, String title, String description, Double price, JobPackage.RateType rateType) {
        JobPackage jobPackage = new JobPackage(postId, title, description, price, rateType);
        em.persist(jobPackage);
        return jobPackage;
    }

    @Override
    public Optional<JobPackage> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<JobPackage> findByPostId(long id, int page) {
        return null;
    }

    @Override
    public boolean deletePackage(long id) {
        return false;
    }

    @Override
    public boolean updatePackage(long id, String title, String description, Double price, JobPackage.RateType rateType) {
        return false;
    }
}
