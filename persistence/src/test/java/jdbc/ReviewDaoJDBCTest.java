package jdbc;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jdbc.ReviewDaoJDBC;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:db_data_test.sql")
public class ReviewDaoJDBCTest {
    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());
    private static final List<UserAuth.Role> USER1_ROLES = Arrays.asList(UserAuth.Role.CLIENT, UserAuth.Role.PROFESSIONAL);
    private static final User USER2 = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true,
            LocalDateTime.now());
    private static final User USER3 = new User(
            5,
            "juliansicardi@gmail.com",
            "Julian Sicardi",
            "123123123",
            true,
            true,
            LocalDateTime.now());
    private static final List<UserAuth.Role> USER2_ROLES = Arrays.asList(UserAuth.Role.CLIENT);
    private static final List<JobPost.Zone> ZONES = new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1], JobPost.Zone.values()[2]));
    private static final JobPost JOB_POST = new JobPost(1, USER1, "Electricista Matriculado", "Lun a Viernes 10hs - 14hs", JobPost.JobType.values()[1], ZONES, 0.0, true, LocalDateTime.now());
    private static final JobPackage JOB_PACKAGE = new JobPackage(
            1,
            1,
            "Trabajo Simple",
            "Arreglos de tomacorrientes",
            200.00, JobPackage.RateType.values()[0],
            true
    );
    private static final JobContract JOB_CONTRACT = new JobContract(
            1, USER2,
            JOB_PACKAGE,
            USER1,
            LocalDateTime.now(),
            "Se me rompio una zapatilla",
            null
    );
    private static final LocalDateTime date = LocalDateTime.now();
    private static final Review REVIEW_1 = new Review(
            4,
            "Muy bueno",
            "Resolvio todo en cuestion de minutos", USER2, JOB_POST, date
    );
    private static final Review REVIEW_2 = new Review(
            2, "Medio pelo", "Resolvio todo de forma ideal", USER3, JOB_POST, date
    );

    @Autowired
    private DataSource ds;

    @InjectMocks
    @Autowired
    private ReviewDaoJDBC reviewDaoJDBC;

    @Test
    public void testCreate() {
        Review maybeReview = new Review(REVIEW_1.getRate(), REVIEW_1.getTitle(), REVIEW_1.getDescription(),
                REVIEW_1.getClient(), REVIEW_1.getJobPost(), date);

        Assert.assertNotNull(maybeReview);
        Assert.assertEquals(REVIEW_1.getTitle(), maybeReview.getTitle());
        Assert.assertEquals(REVIEW_1.getDescription(), maybeReview.getDescription());
        Assert.assertEquals(REVIEW_1.getRate(), maybeReview.getRate());
        Assert.assertEquals(REVIEW_1.getClient(), maybeReview.getClient());
        Assert.assertEquals(REVIEW_1.getJobPost(), maybeReview.getJobPost());
        Assert.assertEquals(REVIEW_1, maybeReview);
    }

    @Test
    public void testFindReviewsByPostId() {
        List<Review> maybePostReviews = reviewDaoJDBC.findReviewsByPostId(JOB_POST.getId(), 0);

        Assert.assertEquals(2, maybePostReviews.size());

        Assert.assertEquals(REVIEW_1.getTitle(), maybePostReviews.get(0).getTitle());
        Assert.assertEquals(REVIEW_1.getDescription(), maybePostReviews.get(0).getDescription());
        Assert.assertEquals(REVIEW_1.getRate(), maybePostReviews.get(0).getRate());
        Assert.assertEquals(REVIEW_1.getClient(), maybePostReviews.get(0).getClient());
        Assert.assertEquals(REVIEW_1.getJobPost(), maybePostReviews.get(0).getJobPost());
        Assert.assertEquals(REVIEW_1, maybePostReviews.get(0));

        Assert.assertEquals(REVIEW_2.getTitle(), maybePostReviews.get(1).getTitle());
        Assert.assertEquals(REVIEW_2.getDescription(), maybePostReviews.get(1).getDescription());
        Assert.assertEquals(REVIEW_2.getRate(), maybePostReviews.get(1).getRate());
        Assert.assertEquals(REVIEW_2.getClient(), maybePostReviews.get(1).getClient());
        Assert.assertEquals(REVIEW_2.getJobPost(), maybePostReviews.get(1).getJobPost());
        Assert.assertEquals(REVIEW_2, maybePostReviews.get(1));
    }

    @Test
    public void testFindReviewsByPostIdSize() {
        List<Review> reviews = reviewDaoJDBC.findReviewsByPostId(JOB_POST.getId(), 0);

        int maybeReviewsByPostIdSize = reviewDaoJDBC.findJobPostReviewsSize(JOB_POST.getId());
        Assert.assertEquals(reviews.size(), maybeReviewsByPostIdSize);
    }

    @Test
    public void testFindJobPostAvgRate() {
        List<Review> reviews = reviewDaoJDBC.findReviewsByPostId(JOB_POST.getId(), 0);

        double maybeAvg = reviewDaoJDBC.findJobPostAvgRate(JOB_POST.getId());
        Assert.assertEquals(reviews.stream().mapToDouble(Review::getRate).average().orElse(0), maybeAvg, 0.0000001);
    }

    @Test
    public void findProfessionalReviews() {
        List<Review> maybeUserReviews = reviewDaoJDBC.findProfessionalReviews(USER1.getId(), 0);

        Assert.assertEquals(2, maybeUserReviews.size());
        Assert.assertEquals(REVIEW_1, maybeUserReviews.get(0));
        Assert.assertEquals(REVIEW_2, maybeUserReviews.get(1));
    }

    @Test
    public void testFindProfessionalAvgRate() {
        List<Review> reviews = reviewDaoJDBC.findProfessionalReviews(USER1.getId(), 0);

        double maybeAvg = reviewDaoJDBC.findProfessionalAvgRate(JOB_POST.getId());
        Assert.assertEquals(reviews.stream().mapToDouble(Review::getRate).average().orElse(0), maybeAvg, 0.0000001);
    }

    @Test
    public void testFindReviewsByPackageId() {
        List<Review> maybeReviews = reviewDaoJDBC.findReviewsByPackageId(JOB_PACKAGE.getId(), 0);

        Assert.assertEquals(2, maybeReviews.size());
        Assert.assertEquals(REVIEW_1, maybeReviews.get(0));
        Assert.assertEquals(REVIEW_2, maybeReviews.get(1));
    }

    @Test
    public void testFindReviewByContractId() {
        Optional<Review> maybeReview = reviewDaoJDBC.findReviewByContractId(JOB_CONTRACT.getId());

        Assert.assertTrue(maybeReview.isPresent());
        Assert.assertEquals(REVIEW_1, maybeReview.get());
    }

}
