package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SimpleReviewService implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public Review create(long contractId, int rate, String title, String description) {
        return reviewDao.create(contractId, rate, title, description);
    }

    @Override
    public List<Review> findReviewsByPostId(long id) {
        return reviewDao.findReviewsByPostId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<Review> findReviewsByPostId(long id, int page) {
        return reviewDao.findReviewsByPostId(id, page);
    }

    @Override
    public List<Review> findReviewsByProId(long id) {
        return reviewDao.findReviewsByProId(id, HirenetUtils.ALL_PAGES);
    }

    public List<Review> findReviewsByProId(long id, int page) {
        return reviewDao.findReviewsByProId(id, page);
    }


    @Override
    public Double findProfessionalAvgRate(long id) {
        return reviewDao.findProfessionalAvgRate(id);
    }

    @Override
    public List<Integer> findProfessionalReviewsByPoints(long id) {
        List<Integer> answer = new ArrayList<>();
        while (answer.size() < 5)
            answer.add(0);

        findReviewsByProId(id).forEach((review) ->
                answer.set(review.getRate() - 1, answer.get(review.getRate() - 1) + 1));

        return answer;
    }

    @Override
    public int findJobPostReviewsSize(long id) {
        return reviewDao.findJobPostReviewsSize(id);
    }

    @Override
    public Optional<Review> findContractReview(long id) {
        return reviewDao.findReviewByContractId(id);
    }

    @Override
    public int findReviewsByProIdMaxPage(long id) {
        return reviewDao.findReviewsByProIdMaxPage(id);
    }

    @Override
    public int findReviewsByProIdSize(long id) {
        return reviewDao.findReviewsByProIdSize(id);
    }

    @Override
    public int findByPostIdMaxPage(long id) {
        return reviewDao.findReviewsByPostIdMaxPage(id);
    }

    @Override
    public Double findJobPostAvgRate(long id) {
        return reviewDao.findJobPostAvgRate(id);
    }

    @Override
    public int findReviewsMaxPage(Long userId,Long postId, String role) {
        validateParameters(userId,role,postId);

        if(userId != null){
            if(role.equalsIgnoreCase("professional"))
                return reviewDao.findReviewsByProIdMaxPage(userId);
            else
                return reviewDao.findReviewsByClientIdMaxPage(userId);
        }
        if(postId != null){
            return reviewDao.findReviewsByPostIdMaxPage(postId);
        }
        return reviewDao.findReviewsMaxPage();
    }

    @Override
    public List<Review> findReviews(Long userId, String role, Long postId, int page) {
        validateParameters(userId,role,postId);

        if(userId != null){
            if(role.equalsIgnoreCase("professional"))
                return reviewDao.findReviewsByProId(userId,page);
            else
                return reviewDao.findReviewsByClientId(userId,page);
        }
        if(postId != null){
            return reviewDao.findReviewsByPostId(postId,page);
        }
        return reviewDao.findAllReviews(page);
    }

    private void validateParameters(Long userId, String role, Long postId){
        if((userId != null || role != null) && postId != null )
            throw new IllegalArgumentException("Incompatible Query Parameters");

        if(userId != null && role == null)
            throw new IllegalArgumentException("Must set Query parameter role for userId");

        if(role != null && !role.equalsIgnoreCase("professional") && !role.equalsIgnoreCase("client"))
            throw new IllegalArgumentException("Invalid role value");
        return;
    }

}
