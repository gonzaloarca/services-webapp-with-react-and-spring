package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> findProfessionalReviews(long id);

    Double findProfessionalAvgRate(long id);

    List<Integer> findProfessionalReviewsByPoints(long id);

    int findJobPostReviewsSize(long id);

    Review findContractReview(long id);

}
