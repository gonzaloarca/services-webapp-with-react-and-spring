package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    List<Review> findAllReviews(long id);

    int findJobPostReviewsSize(long id);

    List<Review> findReviews(long id);

    Optional<Review> findReviewByContractId(long id);

    Double findProfessionalAvgRate(long id);

    List<Review> findProfessionalReviews(long id);

}
