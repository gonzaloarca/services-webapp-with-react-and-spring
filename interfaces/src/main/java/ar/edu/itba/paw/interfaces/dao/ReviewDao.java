package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review create(long contractId, int rate, String title, String description);

    List<Review> findReviewsByPostId(long id, int page);

    int findJobPostReviewsSize(long id);

    Double findJobPostAvgRate(long id);

    List<Review> findReviewsByPackageId(long id, int page);

    Double findProfessionalAvgRate(long id);

    List<Review> findReviewsByProId(long id, int page);

    Optional<Review> findReviewByContractId(long id);

    int findReviewsByProIdMaxPage(long id);

    int findReviewsByProIdSize(long id);

    int findReviewsByPostIdMaxPage(long id);

    int findReviewsByClientIdMaxPage(long userId);

    int findReviewsMaxPage();

    List<Review> findReviewsByClientId(long userId, int page);

    List<Review> findAllReviews(int page);
}
