package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.ReviewDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
import ar.edu.itba.paw.models.exceptions.JobContractNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:review_data_test.sql")
@Transactional
public class ReviewDaoJpaTest {
    private static final LocalDateTime creationDate = LocalDateTime.now();
    private static final User PROFESSIONAL = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());
    private static final User CLIENT = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true,
            LocalDateTime.now());
    private static final List<JobPost.Zone> ZONES =
            new ArrayList<JobPost.Zone>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));
    private static final JobPost JOB_POST = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            true,
            LocalDateTime.now()
    );

    private static final JobPackage[] JOB_PACKAGES = {
            new JobPackage(
                    1,
                    JOB_POST,
                    "Trabajo simple",
                    "Arreglo basico de electrodomesticos",
                    200.0,
                    JobPackage.RateType.values()[0],
                    true
            ), new JobPackage(
            2,
            JOB_POST,
            "Trabajo no tan simple",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            3,
            JOB_POST,
            "Trabajo simple 2",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            4,
            JOB_POST,
            "Trabajo no tan simple 2",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            5,
            JOB_POST,
            "Trabajo Complejo 2",
            "Arreglos de canerias",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            6,
            JOB_POST,
            "Trabajo barato 2",
            "Arreglos varios",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            7,
            JOB_POST,
            "Trabajo barato 2",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            8,
            JOB_POST,
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            9,
            JOB_POST,
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    )
    };

    private static final JobContract[] JOB_CONTRACTS_PACKAGE1 = new JobContract[]{
            new JobContract(1, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio una zapatilla"),
            new JobContract(2, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Arreglo de fusibles facil"),
            new JobContract(3, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Arreglo de fusibles"),
            new JobContract(4, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio una zapatilla"),
            new JobContract(5, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Arreglo de fusibles facil"),
            new JobContract(6, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Arreglo de fusibles"),
            new JobContract(7, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Instalacion de tomacorrientes"),
            new JobContract(8, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio una tuberia en la cocina"),
            new JobContract(9, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompieron las tuberias del baño"),
            new JobContract(10, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio la caldera"),
            new JobContract(11, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio la caldera denuevo"),
            new JobContract(12, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio la caldera denuevo")
    };

    private static final Review[] REVIEWS = new Review[]{
            new Review(4, "Muy bueno", "Resolvio todo en cuestion de minutos", JOB_CONTRACTS_PACKAGE1[0], creationDate),
            new Review(4, "Muy bueno", "Resolvio todo en cuestion de minutos", JOB_CONTRACTS_PACKAGE1[1], creationDate),
            new Review(2, "Medio pelo", "Resolvio todo de forma ideal", JOB_CONTRACTS_PACKAGE1[2], creationDate),
            new Review(2, "Medio pelo", "Resolvio todo de forma ideal", JOB_CONTRACTS_PACKAGE1[3], creationDate),
            new Review(4, "Muy bueno", "Resolvio todo en cuestion de minutos", JOB_CONTRACTS_PACKAGE1[4], creationDate),
            new Review(2, "Medio pelo", "Resolvio todo de forma ideal", JOB_CONTRACTS_PACKAGE1[5], creationDate),
            new Review(4, "Muy bueno", "Resolvio todo en cuestion de minutos", JOB_CONTRACTS_PACKAGE1[6], creationDate),
            new Review(2, "Medio pelo", "Resolvio todo de forma ideal", JOB_CONTRACTS_PACKAGE1[7], creationDate),
            new Review(4, "Muy bueno", "Resolvio todo en cuestion de minutos", JOB_CONTRACTS_PACKAGE1[8], creationDate),
            new Review(5, "Muy bueno", "Resolvio todo en cuestion de minutos", JOB_CONTRACTS_PACKAGE1[9], creationDate),


    };


    private static final int MAX_PAGE_FOR_PRO = 2;

    private static final int TOTAL_REVIEW_COUNT_PRO = 10;

    private static final int TOTAL_REVIEWS = 9;

    private static final int MAX_PAGE_FOR_POST = 2;

    private static final int MAX_REVIEWS_FOR_PACKAGE = 10;

    private static final int REVIEW_POST_COUNT = 10;

    private static final int REVIEW_POST_FIRST_PAGE_COUNT = 8;

    private static final double REVIEW_POST_1_AVG = 3.3;

    private static final long NON_EXISTENT_ID = 999;


    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReviewDaoJpa reviewDaoJpa;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {
        Review newReview = new Review(4, "Muy bueno!", "Execelnte servicio", JOB_CONTRACTS_PACKAGE1[10], LocalDateTime.now());

        Review maybeReview = reviewDaoJpa.create(11, newReview.getRate(), newReview.getTitle(), newReview.getDescription());

        Assert.assertNotNull(maybeReview);
        Assert.assertEquals(newReview.getTitle(), maybeReview.getTitle());
        Assert.assertEquals(newReview.getDescription(), maybeReview.getDescription());
        Assert.assertEquals(newReview.getRate(), maybeReview.getRate());
        Assert.assertEquals(newReview.getClient().getId(), maybeReview.getClient().getId());
        Assert.assertEquals(newReview.getJobPost().getId(), maybeReview.getJobPost().getId());
        Assert.assertEquals(newReview.getJobContract(), maybeReview.getJobContract());
        Assert.assertEquals(newReview, maybeReview);
        Assert.assertEquals(TOTAL_REVIEWS + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "review"));
    }

    @Test(expected = PersistenceException.class)
    public void testCreateWithNullDescription() {
        Review newReview = new Review(4, "Muy bueno!", null, JOB_CONTRACTS_PACKAGE1[10], LocalDateTime.now());

        reviewDaoJpa.create(11, newReview.getRate(), newReview.getTitle(), newReview.getDescription());
    }

    @Test(expected = JobContractNotFoundException.class)
    public void testCreateWithNonExistentContract() {
        reviewDaoJpa.create(NON_EXISTENT_ID, 1, "title", "description");
    }

    @Test
    public void testFindReviewsByPostIdWithoutPagination() {

        List<Review> maybePostReviews = reviewDaoJpa.findReviewsByPostId(JOB_POST.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(REVIEW_POST_COUNT, maybePostReviews.size());

        for (int i = 0; i < maybePostReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getTitle(), maybePostReviews.get(i).getTitle());
            Assert.assertEquals(REVIEWS[i].getDescription(), maybePostReviews.get(i).getDescription());
            Assert.assertEquals(REVIEWS[i].getRate(), maybePostReviews.get(i).getRate());
            Assert.assertEquals(REVIEWS[i].getClient().getId(), maybePostReviews.get(i).getClient().getId());
            Assert.assertEquals(REVIEWS[i].getJobPost().getId(), maybePostReviews.get(i).getJobPost().getId());
            Assert.assertEquals(REVIEWS[i].getJobContract().getId(), maybePostReviews.get(i).getJobContract().getId());
        }
    }

    @Test
    public void testFindReviewsByNonExistingPostIdWithoutPagination() {

        List<Review> maybePostReviews = reviewDaoJpa.findReviewsByPostId(NON_EXISTENT_ID, HirenetUtils.ALL_PAGES);

        Assert.assertEquals(0, maybePostReviews.size());
    }

    @Test
    public void testFindReviewsByPostIdWithPagination() {
        List<Review> maybePostReviews = reviewDaoJpa.findReviewsByPostId(JOB_POST.getId(), 0);

        Assert.assertEquals(REVIEW_POST_FIRST_PAGE_COUNT, maybePostReviews.size());

        for (int i = 0; i < maybePostReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getTitle(), maybePostReviews.get(i).getTitle());
            Assert.assertEquals(REVIEWS[i].getDescription(), maybePostReviews.get(i).getDescription());
            Assert.assertEquals(REVIEWS[i].getRate(), maybePostReviews.get(i).getRate());
            Assert.assertEquals(REVIEWS[i].getClient().getId(), maybePostReviews.get(i).getClient().getId());
            Assert.assertEquals(REVIEWS[i].getJobPost().getId(), maybePostReviews.get(i).getJobPost().getId());
            Assert.assertEquals(REVIEWS[i].getJobContract().getId(), maybePostReviews.get(i).getJobContract().getId());
        }
    }

    @Test
    public void testFindReviewsByNonExistingPostIdWithPagination() {

        List<Review> maybePostReviews = reviewDaoJpa.findReviewsByPostId(NON_EXISTENT_ID, 0);

        Assert.assertEquals(0, maybePostReviews.size());
    }

    @Test
    public void testFindReviewsByPostIdWithPaginationNonExistingPage() {

        List<Review> maybePostReviews = reviewDaoJpa.findReviewsByPostId(JOB_POST.getId(), 99999);

        Assert.assertEquals(0, maybePostReviews.size());
    }

    @Test
    public void testFindReviewsByPostIdSize() {

        int maybeReviewsByPostIdSize = reviewDaoJpa.findJobPostReviewsSize(JOB_POST.getId());

        Assert.assertEquals(REVIEWS.length, maybeReviewsByPostIdSize);
    }

    @Test
    public void testFindReviewsByNonExistingPostIdSize() {

        int maybeReviewsByPostIdSize = reviewDaoJpa.findJobPostReviewsSize(NON_EXISTENT_ID);

        Assert.assertEquals(0, maybeReviewsByPostIdSize);
    }

    @Test
    public void testFindJobPostAvgRate() {

        double maybeAvg = reviewDaoJpa.findJobPostAvgRate(JOB_POST.getId());

        Assert.assertEquals(REVIEW_POST_1_AVG, maybeAvg, 0.0001);
    }

    @Test
    public void testFindNonExistingJobPostAvgRate() {

        double maybeAvg = reviewDaoJpa.findJobPostAvgRate(NON_EXISTENT_ID);

        Assert.assertEquals(0, maybeAvg, 0.0001);
    }

    @Test
    public void findProfessionalReviewsWithoutPagination() {

        List<Review> maybeUserReviews = reviewDaoJpa.findReviewsByProId(PROFESSIONAL.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(TOTAL_REVIEW_COUNT_PRO, maybeUserReviews.size());
        for (int i = 0; i < maybeUserReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getJobContract().getId(), maybeUserReviews.get(i).getJobContract().getId());
        }
    }

    @Test
    public void findNonExistingProfessionalReviewsWithoutPagination() {

        List<Review> maybeUserReviews = reviewDaoJpa.findReviewsByProId(NON_EXISTENT_ID, HirenetUtils.ALL_PAGES);

        Assert.assertEquals(0, maybeUserReviews.size());
    }

    @Test
    public void findProfessionalReviewsWithPagination() {

        List<Review> maybeUserReviews = reviewDaoJpa.findReviewsByProId(PROFESSIONAL.getId(), 0);

        Assert.assertEquals(REVIEW_POST_FIRST_PAGE_COUNT, maybeUserReviews.size());
        for (int i = 0; i < maybeUserReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getJobContract().getId(), maybeUserReviews.get(i).getJobContract().getId());
        }
    }

    @Test
    public void findNonExistingProfessionalReviewsWithPagination() {

        List<Review> maybeUserReviews = reviewDaoJpa.findReviewsByProId(NON_EXISTENT_ID, 0);

        Assert.assertEquals(0, maybeUserReviews.size());
    }

    @Test
    public void testFindProfessionalAvgRate() {

        double maybeAvg = reviewDaoJpa.findProfessionalAvgRate(JOB_POST.getId());

        Assert.assertEquals(REVIEW_POST_1_AVG, maybeAvg, 0.0001);
    }

    @Test
    public void testFindNonExistingProfessionalAvgRate() {

        double maybeAvg = reviewDaoJpa.findProfessionalAvgRate(JOB_POST.getId());

        Assert.assertEquals(REVIEW_POST_1_AVG, maybeAvg, 0.0001);
    }

    @Test
    public void testFindReviewsByPackageIdWithoutPagination() {

        List<Review> maybeReviews = reviewDaoJpa.findReviewsByPackageId(JOB_PACKAGES[0].getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(MAX_REVIEWS_FOR_PACKAGE, maybeReviews.size());
        for (int i = 0; i < maybeReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getJobContract().getId(), maybeReviews.get(i).getJobContract().getId());
        }

    }

    @Test
    public void testFindReviewsByNonExistingPackageIdWithoutPagination() {

        List<Review> maybeReviews = reviewDaoJpa.findReviewsByPackageId(NON_EXISTENT_ID, HirenetUtils.ALL_PAGES);

        Assert.assertEquals(0, maybeReviews.size());
    }

    @Test
    public void testFindReviewsByPackageIdWithPagination() {

        List<Review> maybeReviews = reviewDaoJpa.findReviewsByPackageId(JOB_PACKAGES[0].getId(), 0);

        Assert.assertEquals(REVIEW_POST_FIRST_PAGE_COUNT, maybeReviews.size());
        for (int i = 0; i < maybeReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getJobContract().getId(), maybeReviews.get(i).getJobContract().getId());
        }

    }

    @Test
    public void testFindReviewsByNonExistingPackageIdWithPagination() {

        List<Review> maybeReviews = reviewDaoJpa.findReviewsByPackageId(NON_EXISTENT_ID, 0);

        Assert.assertEquals(0, maybeReviews.size());
    }

    @Test
    public void testFindReviewsByPackageIdWithPaginationNonExistingPage() {

        List<Review> maybeReviews = reviewDaoJpa.findReviewsByPackageId(JOB_PACKAGES[0].getId(), 99999);

        Assert.assertEquals(0, maybeReviews.size());
    }

    @Test
    public void testFindReviewByContractId() {
        Optional<Review> maybeReview = reviewDaoJpa.findReviewByContractId(JOB_CONTRACTS_PACKAGE1[0].getId());

        Assert.assertTrue(maybeReview.isPresent());
        Assert.assertEquals(REVIEWS[0].getJobContract().getId(), maybeReview.get().getJobContract().getId());
    }

    @Test
    public void testFindReviewByNonExistingContractId() {

        Optional<Review> maybeReview = reviewDaoJpa.findReviewByContractId(NON_EXISTENT_ID);

        Assert.assertFalse(maybeReview.isPresent());
    }

    @Test
    public void testFindMaxPageReviewsByUserId() {

        int maxPage = reviewDaoJpa.findMaxPageReviewsByProId(PROFESSIONAL.getId());

        Assert.assertEquals(MAX_PAGE_FOR_PRO, maxPage);
    }

    @Test
    public void testFindMaxPageReviewsByNonExistingUserId() {

        int maxPage = reviewDaoJpa.findMaxPageReviewsByProId(NON_EXISTENT_ID);

        Assert.assertEquals(0, maxPage);
    }

    @Test
    public void testFindProfessionalReviewsSize() {

        int size = reviewDaoJpa.findReviewsSizeByProId(PROFESSIONAL.getId());

        Assert.assertEquals(TOTAL_REVIEW_COUNT_PRO, size);
    }

    @Test
    public void testFindNonExistingProfessionalReviewsSize() {

        int size = reviewDaoJpa.findReviewsSizeByProId(NON_EXISTENT_ID);

        Assert.assertEquals(0, size);
    }

    @Test
    public void testFindMaxPageReviewsByPostId() {

        int maxPage = reviewDaoJpa.findMaxPageReviewsByPostId(JOB_POST.getId());

        Assert.assertEquals(MAX_PAGE_FOR_POST, maxPage);
    }

    @Test
    public void testFindMaxPageReviewsByNonExistingPostId() {

        int maxPage = reviewDaoJpa.findMaxPageReviewsByPostId(NON_EXISTENT_ID);

        Assert.assertEquals(0, maxPage);
    }

}
