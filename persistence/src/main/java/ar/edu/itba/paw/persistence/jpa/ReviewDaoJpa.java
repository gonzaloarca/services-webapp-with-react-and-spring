package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.persistence.utils.PagingUtil;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
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
public class ReviewDaoJpa implements ReviewDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Review create(long contractId, int rate, String title, String description) {
        JobContract contract = em.find(JobContract.class, contractId);
        if (contract == null)
            throw new JobContractNotFoundException();

        final Review review = new Review(rate, title, description, contract, LocalDateTime.now());
        em.persist(review);
        return review;
    }

    @Override
    public List<Review> findReviewsByPostId(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract NATURAL JOIN job_package WHERE job_package.post_id = :id ORDER BY review_creation_date DESC").setParameter("id", id);
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public int findJobPostReviewsSize(long id) {
        return em.createQuery("SELECT COUNT(*) FROM Review r WHERE r.jobContract.jobPackage.jobPost.id = :id", Long.class)
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0L).intValue();
    }

    @Override
    public Double findJobPostAvgRate(long id) {
        return em.createQuery("SELECT coalesce(avg(r.rate),0) FROM Review r WHERE r.jobContract.jobPackage.jobPost.id = :id", Double.class)
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0.0);
    }

    @Override
    public List<Review> findReviewsByPackageId(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract WHERE package_id = :id ORDER BY review_creation_date DESC")
                .setParameter("id", id);
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Double findProfessionalAvgRate(long id) {
        return em.createQuery("SELECT coalesce(avg(r.rate),0) FROM Review r WHERE r.jobContract.jobPackage.jobPost.user.id = :id", Double.class)
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0.0);
    }

    @Override
    public List<Review> findReviewsByProId(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract NATURAL JOIN job_package NATURAL JOIN job_post WHERE user_id = :id ORDER BY review_creation_date DESC")
                .setParameter("id", id);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Optional<Review> findReviewByContractId(long id) {
        return em.createQuery("FROM Review AS r WHERE r.jobContract.id = :id", Review.class).setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public int findReviewsByProIdMaxPage(long id) {
        Long reviewCount = em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.user.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil(((double) reviewCount) / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findReviewsByProIdSize(long id) {
        Long res = em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.user.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return res.intValue();
    }

    @Override
    public int findReviewsByPostIdMaxPage(long id) {
        Long aux = em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil(((double) aux.intValue())/ HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findReviewsByClientIdMaxPage(long userId) {
        Long aux = em.createQuery("SELECT count(*) from Review as r where r.jobContract.client.id = :id", Long.class)
                .setParameter("id", userId).getResultList().stream().findFirst().orElse(0L);
        return (int) Math.ceil(((double) aux.intValue())/ HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findReviewsMaxPage() {
        Long aux = em.createQuery("SELECT count(*) from Review", Long.class)
                .getResultList().stream().findFirst().orElse(0L);
        return (int) Math.ceil(((double) aux.intValue())/ HirenetUtils.PAGE_SIZE);
    }

    @Override
    public List<Review> findReviewsByClientId(long userId, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract WHERE client_id = :id ORDER BY review_creation_date DESC")
                .setParameter("id", userId);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public List<Review> findAllReviews(int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review ORDER BY contract_id ASC");

        return executePageQuery(page, nativeQuery);
    }

    private List<Review> executePageQuery(int page, Query nativeQuery) {
        List<Long> filteredIds = PagingUtil.getFilteredIds(page, nativeQuery);

        return em.createQuery("FROM Review AS r WHERE r.jobContract.id IN :filteredIds", Review.class)
                .setParameter("filteredIds", (filteredIds.isEmpty())? null : filteredIds).getResultList().stream().sorted(
                        //Ordenamos los elementos segun el orden de filteredIds
                        Comparator.comparingInt(o -> filteredIds.indexOf(o.getJobContract().getId()))
                ).collect(Collectors.toList());
    }
}
