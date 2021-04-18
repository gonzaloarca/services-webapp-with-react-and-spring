package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> findProfessionalReviews(long id);

    Double getProfessionalAvgRate(long id);

    List<Integer> getProfessionalReviewsByPoints(long id);

    int getJobPostReviewsSize(long id);
}
