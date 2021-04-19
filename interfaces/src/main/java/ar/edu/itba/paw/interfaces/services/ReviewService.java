package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review create(long contractId, int rate, String title, String description);

    List<Review> findProfessionalReviews(long id);

    Double findProfessionalAvgRate(long id);

    List<Integer> findProfessionalReviewsByPoints(long id);

    int findJobPostReviewsSize(long id);

    Optional<Review> findContractReview(long id);

}
