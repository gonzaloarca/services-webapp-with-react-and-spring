//package jdbc;
//
//import ar.edu.itba.paw.interfaces.HirenetUtils;
//import ar.edu.itba.paw.models.*;
//import ar.edu.itba.paw.persistence.jdbc.JobContractDaoJDBC;
//import ar.edu.itba.paw.persistence.jdbc.JobPostDaoJDBC;
//import ar.edu.itba.paw.persistence.jdbc.ReviewDaoJDBC;
//import config.TestConfig;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.omg.PortableServer.POA;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.sql.DataSource;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Rollback
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql("classpath:review_data_test.sql")
//public class ReviewDaoJDBCTest {
//    private static final LocalDateTime creationDate = LocalDateTime.now();
//    private static final User PROFESSIONAL = new User(
//            1,
//            "franquesada@gmail.com",
//            "Francisco Quesada",
//            "1147895678",
//            true,
//            true,
//            LocalDateTime.now());
//    private static final User CLIENT = new User(
//            2,
//            "manurodriguez@gmail.com",
//            "Manuel Rodriguez",
//            "1109675432",
//            true,
//            true,
//            LocalDateTime.now());
//    private static final List<JobPost.Zone> ZONES = new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1], JobPost.Zone.values()[2]));
//    private static final JobPost JOB_POST = new JobPost(
//            1,
//            PROFESSIONAL,
//            "Electricista Matriculado",
//            "Lun a Viernes 10hs - 14hs",
//            JobPost.JobType.values()[1],
//            ZONES, 0.0,
//            true,
//            LocalDateTime.now()
//    );
//
//    private static final JobPackage[] JOB_PACKAGES = {
//            new JobPackage(
//                    1,
//                    JOB_POST.getId(),
//                    "Trabajo simple",
//                    "Arreglo basico de electrodomesticos",
//                    200.0,
//                    JobPackage.RateType.values()[0],
//                    true
//            ), new JobPackage(
//            2,
//            JOB_POST.getId(),
//            "Trabajo no tan simple",
//            "Instalacion de cableado electrico",
//            850.00,
//            JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            3,
//            JOB_POST.getId(),
//            "Trabajo simple 2",
//            "Arreglo basico de electrodomesticos",
//            200.0,
//            JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            4,
//            JOB_POST.getId(),
//            "Trabajo no tan simple 2",
//            "Instalacion de cableado electrico",
//            850.00,
//            JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            5,
//            JOB_POST.getId(),
//            "Trabajo Complejo 2",
//            "Arreglos de canerias",
//            500.00,
//            JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            6,
//            JOB_POST.getId(),
//            "Trabajo barato 2",
//            "Arreglos varios",
//            500.00,
//            JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            7,
//            JOB_POST.getId(),
//            "Trabajo barato 2",
//            "Arreglos varios",
//            500.00, JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            8,
//            JOB_POST.getId(),
//            "Trabajo Experto 2",
//            "Presupuesto y desarrollo de proyectos",
//            500.00,
//            JobPackage.RateType.values()[0],
//            true
//    ), new JobPackage(
//            9,
//            JOB_POST.getId(),
//            "Trabajo Experto 2",
//            "Presupuesto y desarrollo de proyectos",
//            500.00,
//            JobPackage.RateType.values()[0],
//            true
//    )
//    };
//
//    private static final byte[] IMAGE_DATA = "image_data_for_testing".getBytes(StandardCharsets.UTF_8);
//
//    private static final String IMAGE_TYPE = "image/jpg";
//
//    private static final JobContract[] JOB_CONTRACTS_PACKAGE1 = new JobContract[]{
//            new JobContract(1, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio una zapatilla", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(2, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles facil", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(3, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(4, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio una zapatilla", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(5, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles facil", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(6, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(7, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Instalacion de tomacorrientes", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(8, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio una tuberia en la cocina", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(9, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompieron las tuberias del baño", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(10, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio la caldera", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(11, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio la caldera denuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
//            new JobContract(12, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio la caldera denuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null)
//    };
//
//    private static final Review[] REVIEWS = new Review[]{
//            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[0],creationDate),
//            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[1],creationDate),
//            new Review(2,"Medio pelo","Resolvio todo de forma ideal",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[2],creationDate),
//            new Review(2,"Medio pelo","Resolvio todo de forma ideal",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[3],creationDate),
//            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[4],creationDate),
//            new Review(2,"Medio pelo","Resolvio todo de forma ideal",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[5],creationDate),
//            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[6],creationDate),
//            new Review(2,"Medio pelo","Resolvio todo de forma ideal",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[7],creationDate),
//            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[8],creationDate),
//            new Review(5,"Muy bueno","Resolvio todo en cuestion de minutos",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[9],creationDate),
//
//
//    };
//
//
//    private static final int MAX_PAGE_FOR_PRO = 2;
//
//    private static final int TOTAL_REVIEW_COUNT_PRO = 10;
//
//    private static final int MAX_PAGE_FOR_POST = 2;
//
//    private static final int MAX_REVIEWS_FOR_PACKAGE = 10;
//
//    private static final int REVIEW_POST_COUNT = 10;
//
//    private static final int REVIEW_POST_FIRST_PAGE_COUNT = 8;
//
//    private static final double REVIEW_POST_1_AVG = 3.3;
//
//    private static final long NON_EXISTENT_ID = 999;
//
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Mock
//    private JobPostDaoJDBC mockPostDao;
//
//    @Mock
//    private JobContractDaoJDBC mockContractDao;
//
//    @InjectMocks
//    @Autowired
//    private ReviewDaoJDBC reviewDaoJDBC;
//
//    @Before
//    public void setUp() {
//        jdbcTemplate = new JdbcTemplate(ds);
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testCreate() {
//
//       Mockito.when(mockContractDao.findById(Mockito.eq(JOB_CONTRACTS_PACKAGE1[10].getId()))).thenReturn(Optional.of(JOB_CONTRACTS_PACKAGE1[10]));
//       Mockito.when(mockPostDao.findById(Mockito.eq(JOB_CONTRACTS_PACKAGE1[11].getJobPackage().getPostId()))).thenReturn(Optional.of(JOB_POST));
//
//        Review newReview = new Review(4,"Muy bueno!","Execelnte servicio",CLIENT,JOB_POST,JOB_CONTRACTS_PACKAGE1[10],LocalDateTime.now());
//
//        Review maybeReview =reviewDaoJDBC.create(11,newReview.getRate(),newReview.getTitle(),newReview.getDescription());
//
//        Assert.assertNotNull(maybeReview);
//        Assert.assertEquals(newReview.getTitle(), maybeReview.getTitle());
//        Assert.assertEquals(newReview.getDescription(), maybeReview.getDescription());
//        Assert.assertEquals(newReview.getRate(), maybeReview.getRate());
//        Assert.assertEquals(newReview.getClient(), maybeReview.getClient());
//        Assert.assertEquals(newReview.getJobPost(), maybeReview.getJobPost());
//        Assert.assertEquals(newReview.getJobContract(), maybeReview.getJobContract());
//        Assert.assertEquals(newReview, maybeReview);
//    }
//
//    @Test
//    public void testFindReviewsByPostIdWithoutPagination() {
//        List<Review> maybePostReviews = reviewDaoJDBC.findReviewsByPostId(JOB_POST.getId(), HirenetUtils.ALL_PAGES);
//
//        Assert.assertEquals(REVIEW_POST_COUNT, maybePostReviews.size());
//
//        for (int i = 0; i < maybePostReviews.size(); i++) {
//            Assert.assertEquals(REVIEWS[i].getTitle(), maybePostReviews.get(i).getTitle());
//            Assert.assertEquals(REVIEWS[i].getDescription(), maybePostReviews.get(i).getDescription());
//            Assert.assertEquals(REVIEWS[i].getRate(), maybePostReviews.get(i).getRate());
//            Assert.assertEquals(REVIEWS[i].getClient(), maybePostReviews.get(i).getClient());
//            Assert.assertEquals(REVIEWS[i].getJobPost(), maybePostReviews.get(i).getJobPost());
//            Assert.assertEquals(REVIEWS[i].getJobContract(), maybePostReviews.get(i).getJobContract());
//            Assert.assertEquals(REVIEWS[i], maybePostReviews.get(i));
//        }
//    }
//
//    @Test
//    public void testFindReviewsByPostIdWithPagination() {
//        List<Review> maybePostReviews = reviewDaoJDBC.findReviewsByPostId(JOB_POST.getId(), 0);
//
//        Assert.assertEquals(REVIEW_POST_FIRST_PAGE_COUNT, maybePostReviews.size());
//
//        for (int i = 0; i < maybePostReviews.size(); i++) {
//            Assert.assertEquals(REVIEWS[i].getTitle(), maybePostReviews.get(i).getTitle());
//            Assert.assertEquals(REVIEWS[i].getDescription(), maybePostReviews.get(i).getDescription());
//            Assert.assertEquals(REVIEWS[i].getRate(), maybePostReviews.get(i).getRate());
//            Assert.assertEquals(REVIEWS[i].getClient(), maybePostReviews.get(i).getClient());
//            Assert.assertEquals(REVIEWS[i].getJobPost(), maybePostReviews.get(i).getJobPost());
//            Assert.assertEquals(REVIEWS[i].getJobContract(), maybePostReviews.get(i).getJobContract());
//            Assert.assertEquals(REVIEWS[i], maybePostReviews.get(i));
//        }
//    }
//
//    @Test
//    public void testFindReviewsByPostIdSize() {
//
//        int maybeReviewsByPostIdSize = reviewDaoJDBC.findJobPostReviewsSize(JOB_POST.getId());
//        Assert.assertEquals(REVIEWS.length, maybeReviewsByPostIdSize);
//    }
//
//    @Test
//    public void testFindJobPostAvgRate() {
//        double maybeAvg = reviewDaoJDBC.findJobPostAvgRate(JOB_POST.getId());
//        Assert.assertEquals(REVIEW_POST_1_AVG, maybeAvg, 0.0001);
//    }
//
//    @Test
//    public void findProfessionalReviewsWithoutPagination() {
//        List<Review> maybeUserReviews = reviewDaoJDBC.findProfessionalReviews(PROFESSIONAL.getId(), HirenetUtils.ALL_PAGES);
//
//        Assert.assertEquals(TOTAL_REVIEW_COUNT_PRO, maybeUserReviews.size());
//        for (int i = 0; i < maybeUserReviews.size(); i++) {
//            Assert.assertEquals(REVIEWS[i], maybeUserReviews.get(i));
//        }
//    }
//
//
//    @Test
//    public void findProfessionalReviewsWithPagination() {
//        List<Review> maybeUserReviews = reviewDaoJDBC.findProfessionalReviews(PROFESSIONAL.getId(), 0);
//
//        Assert.assertEquals(REVIEW_POST_FIRST_PAGE_COUNT, maybeUserReviews.size());
//        for (int i = 0; i < maybeUserReviews.size(); i++) {
//            Assert.assertEquals(REVIEWS[i], maybeUserReviews.get(i));
//        }
//    }
//
//
//
//    @Test
//    public void testFindProfessionalAvgRate() {
//
//        double maybeAvg = reviewDaoJDBC.findProfessionalAvgRate(JOB_POST.getId());
//        Assert.assertEquals(REVIEW_POST_1_AVG, maybeAvg, 0.0001);
//    }
//
//    @Test
//    public void testFindReviewsByPackageIdWithoutPagination() {
//        List<Review> maybeReviews = reviewDaoJDBC.findReviewsByPackageId(JOB_PACKAGES[0].getId(), HirenetUtils.ALL_PAGES);
//
//        Assert.assertEquals(MAX_REVIEWS_FOR_PACKAGE, maybeReviews.size());
//        for (int i = 0; i < maybeReviews.size(); i++) {
//            Assert.assertEquals(REVIEWS[i], maybeReviews.get(i));
//        }
//
//    }
//
//    @Test
//    public void testFindReviewsByPackageIdWithPagination() {
//        List<Review> maybeReviews = reviewDaoJDBC.findReviewsByPackageId(JOB_PACKAGES[0].getId(), 0);
//
//        Assert.assertEquals(REVIEW_POST_FIRST_PAGE_COUNT, maybeReviews.size());
//        for (int i = 0; i < maybeReviews.size(); i++) {
//            Assert.assertEquals(REVIEWS[i], maybeReviews.get(i));
//        }
//
//    }
//
//    @Test
//    public void testFindReviewByContractId() {
//        Optional<Review> maybeReview = reviewDaoJDBC.findReviewByContractId(JOB_CONTRACTS_PACKAGE1[0].getId());
//
//        Assert.assertTrue(maybeReview.isPresent());
//        Assert.assertEquals(REVIEWS[0], maybeReview.get());
//    }
//
//    @Test
//    public void testFindMaxPageReviewsByUserId(){
//        int maxPage = reviewDaoJDBC.findMaxPageReviewsByUserId(PROFESSIONAL.getId());
//        Assert.assertEquals(MAX_PAGE_FOR_PRO,maxPage);
//    }
//
//    @Test
//    public void testFindProfessionalReviewsSize() {
//        int size =  reviewDaoJDBC.findProfessionalReviewsSize(PROFESSIONAL.getId());
//        Assert.assertEquals(TOTAL_REVIEW_COUNT_PRO,size);
//    }
//
//    @Test
//    public void testFindMaxPageReviewsByPostId() {
//        int maxPage = reviewDaoJDBC.findMaxPageReviewsByPostId(JOB_POST.getId());
//        Assert.assertEquals(MAX_PAGE_FOR_POST,maxPage);
//    }
//
//    @Test(expected = DataIntegrityViolationException.class)
//    public void testCreateWithNonExistentContract(){
//        reviewDaoJDBC.create(NON_EXISTENT_ID,1,"title","description");
//    }
//}
