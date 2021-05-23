package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.Review;
import exceptions.JobContractNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract NATURAL JOIN job_package WHERE job_package.post_id = :id").setParameter("id", id);
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public int findJobPostReviewsSize(long id) {
        @SuppressWarnings("unchecked")
        BigInteger size = (BigInteger) em
                .createNativeQuery("SELECT COUNT(*) FROM review NATURAL JOIN contract NATURAL JOIN job_package WHERE post_id = :id")
                .setParameter("id", id).getResultList().stream().findFirst().orElse(-1);
        return size.intValue();
    }

    @Override
    public Double findJobPostAvgRate(long id) {
        @SuppressWarnings("unchecked")
        Double avg = (Double) em.createNativeQuery("SELECT coalesce(avg(CAST(REVIEW_RATE AS FLOAT)),0) as rating " +
                "FROM review NATURAL JOIN contract NATURAL JOIN job_package NATURAL JOIN job_post WHERE post_id = :id GROUP BY post_id")
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0.0);
        return avg;
    }

    @Override
    public List<Review> findReviewsByPackageId(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract WHERE package_id = :id")
                .setParameter("id", id);
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Double findProfessionalAvgRate(long id) {
        @SuppressWarnings("unchecked")
        Double avg = (Double) em.createNativeQuery("SELECT coalesce(avg(CAST(REVIEW_RATE AS FLOAT)),0) as rating FROM review " +
                "NATURAL JOIN contract NATURAL JOIN job_package NATURAL JOIN job_post NATURAL JOIN users WHERE user_id = :id GROUP BY user_id")
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0.0);

        return avg;
    }

    @Override
    public List<Review> findProfessionalReviews(long id, int page) {
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract NATURAL JOIN job_package NATURAL JOIN job_post WHERE user_id = :id")
                .setParameter("id", id);

        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Optional<Review> findReviewByContractId(long id) {
        return em.createQuery("FROM Review AS r WHERE r.jobContract.id = :id", Review.class).setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public int findMaxPageReviewsByUserId(long id) {
        Long reviewCount = (Long) em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.user.id = :id")
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil(((double) reviewCount) / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findProfessionalReviewsSize(long id) {
        Long res = (Long) em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.user.id = :id")
                .setParameter("id", id).getSingleResult();
        return res.intValue();
    }

    @Override
    public int findMaxPageReviewsByPostId(long id) {
        Long aux = (Long) em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.id = :id")
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil(((double) aux.intValue())/ HirenetUtils.PAGE_SIZE);
    }

    private List<Review> executePageQuery(int page, Query nativeQuery) {
        if (page != HirenetUtils.ALL_PAGES) {
            nativeQuery.setFirstResult((page) * HirenetUtils.PAGE_SIZE);
            nativeQuery.setMaxResults(HirenetUtils.PAGE_SIZE);
        }

        @SuppressWarnings("unchecked")
        List<Long> filteredIds = (List<Long>) nativeQuery.getResultList().stream()
                .map(e -> Long.valueOf(e.toString())).collect(Collectors.toList());

        if (filteredIds.isEmpty())
            return new ArrayList<>();

        return em.createQuery("FROM Review AS r WHERE r.jobContract.id IN :filteredIds", Review.class)
                .setParameter("filteredIds", filteredIds).getResultList();
    }
}
