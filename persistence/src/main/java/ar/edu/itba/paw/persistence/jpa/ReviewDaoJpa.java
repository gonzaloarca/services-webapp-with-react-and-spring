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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract NATURAL JOIN job_package WHERE job_package.post_id = :id").setParameter("id", id);
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
        Query nativeQuery = em.createNativeQuery("SELECT contract_id FROM review NATURAL JOIN contract WHERE package_id = :id")
                .setParameter("id", id);
        return executePageQuery(page, nativeQuery);
    }

    @Override
    public Double findProfessionalAvgRate(long id) {
        return em.createQuery("SELECT coalesce(avg(r.rate),0) FROM Review r WHERE r.jobContract.jobPackage.jobPost.user.id = :id", Double.class)
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0.0);
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
        Long reviewCount = em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.user.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return (int) Math.ceil(((double) reviewCount) / HirenetUtils.PAGE_SIZE);
    }

    @Override
    public int findProfessionalReviewsSize(long id) {
        Long res = em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.user.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        return res.intValue();
    }

    @Override
    public int findMaxPageReviewsByPostId(long id) {
        Long aux = em.createQuery("SELECT count(*) from Review as r where r.jobContract.jobPackage.jobPost.id = :id", Long.class)
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
                .setParameter("filteredIds", filteredIds).getResultList().stream().sorted(
                        //Ordenamos los elementos segun el orden de filteredIds
                        Comparator.comparingInt(o -> filteredIds.indexOf(o.getJobContract().getId()))
                ).collect(Collectors.toList());
    }
}
