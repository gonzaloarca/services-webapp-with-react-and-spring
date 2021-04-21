package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review create(long contractId, int rate, String title, String description);

    List<Review> findAllReviews(long id, int page);

    int findJobPostReviewsSize(long id);

    List<Review> findReviews(long id, int page);

    Double findProfessionalAvgRate(long id);

    List<Review> findProfessionalReviews(long id, int page);

    Optional<Review> findReviewByContractId(long id);

}
