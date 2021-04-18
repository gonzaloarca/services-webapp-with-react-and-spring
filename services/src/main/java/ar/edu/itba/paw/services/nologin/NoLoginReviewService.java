package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoLoginReviewService implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public List<Review> findProfessionalReviews(long id) {
        return reviewDao.findProfessionalReviews(id);
    }

    @Override
    public Double getProfessionalAvgRate(long id) {
        return reviewDao.findProfessionalAvgRate(id);
    }

    @Override
    public List<Integer> getProfessionalReviewsByPoints(long id) {
        List<Integer> answer = new ArrayList<>();
        while (answer.size() < 5)
            answer.add(0);
        findProfessionalReviews(id).forEach((review) -> {
            answer.set(review.getRate() - 1, answer.get(review.getRate() - 1) + 1);
        });

        return answer;
    }

    @Override
    public int getJobPostReviewsSize(long id) {
        return reviewDao.findJobPostReviewsSize(id);
    }
}
