package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review create(long contractId, int rate, String title, String description);

    List<Review> findReviewsByPostId(long id, int page);

    List<Review> findReviewsByPostId(long id);

    List<Review> findReviewsByProId(long id);

    List<Review> findReviewsByProId(long id, int page);

    Double findProfessionalAvgRate(long id);

    List<Integer> findProfessionalReviewsByPoints(long id);

    int findJobPostReviewsSize(long id);

    Optional<Review> findContractReview(long id);

    int findMaxPageReviewsByProId(long id);

    int findReviewsSizeByProId(long id);

    int findMaxPageByPostId(long id);

    Double findJobPostAvgRate(long id);

}
