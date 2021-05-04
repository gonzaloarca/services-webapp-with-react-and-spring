package jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:db_data_test.sql")
public class ReviewDaoJDBCTest {
    private static final List<UserAuth.Role> USER1_ROLES = Arrays.asList(UserAuth.Role.CLIENT, UserAuth.Role.PROFESSIONAL);
    private static final List<UserAuth.Role> USER2_ROLES = Arrays.asList(UserAuth.Role.CLIENT);

    private static final LocalDateTime creationDate = LocalDateTime.of(2021, Month.MAY,4,1,9,46);

    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());
    private static final User USER2 = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true,
            LocalDateTime.now());
    private static final List<JobPost.Zone> ZONES = new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1], JobPost.Zone.values()[2]));
    private static final JobPost[] JOB_POSTS = new JobPost[]{
            new JobPost(
                    1,
                    USER1,
                    "Electricista Matriculado",
                    "Lun a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    2,
                    USER1,
                    "Paseador de perros",
                    "Viernes a sabados 09hs - 14hs",
                    JobPost.JobType.values()[3],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    4,
                    USER1,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    5,
                    USER1,
                    "Electricista Matriculado 2",
                    "Lun a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    6,
                    USER1,
                    "Paseador de perros 2",
                    "Viernes a sabados 09hs - 14hs",
                    JobPost.JobType.values()[3],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    7,
                    USER1,
                    "Electricista no matriculado 2",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    8,
                    USER1,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    9,
                    USER1,
                    "Plomero Matriculado 3",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    10,
                    USER1,
                    "Plomero Matriculado 4",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    3,
                    USER2,
                    "Electricista no matriculado",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            )};

    private static final JobPost INACTIVE_JOB_POST = new JobPost(
            11,
            USER1,
            "Plomero Inactivo",
            "Miercoles a Viernes 10hs - 14hs",
            JobPost.JobType.values()[2],
            ZONES, 0.0,
            true,
            LocalDateTime.now()

    );

    private static final JobPackage[] JOB_PACKAGES = {
            new JobPackage(
                    1,
                    JOB_POSTS[0].getId(),
                    "Trabajo simple",
                    "Arreglo basico de electrodomesticos",
                    200.0,
                    JobPackage.RateType.values()[0],
                    true
            ), new JobPackage(
            2,
            JOB_POSTS[0].getId(),
            "Trabajo no tan simple",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            6,
            JOB_POSTS[0].getId(),
            "Trabajo simple 2",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            7,
            JOB_POSTS[0].getId(),
            "Trabajo no tan simple 2",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            8,
            JOB_POSTS[0].getId(),
            "Trabajo Complejo 2",
            "Arreglos de canerias",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            9,
            JOB_POSTS[0].getId(),
            "Trabajo barato 2",
            "Arreglos varios",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            10,
            JOB_POSTS[0].getId(),
            "Trabajo barato 2",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            11,
            JOB_POSTS[0].getId(),
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            12,
            JOB_POSTS[0].getId(),
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    )
    };

    private static final byte[] IMAGE_DATA = "image_data_for_testing".getBytes(StandardCharsets.UTF_8);

    private static final String IMAGE_TYPE = "image/jpg";

    private static final JobContract[] JOB_CONTRACTS_PACKAGE1 = new JobContract[]{
            new JobContract(1,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio una zapatilla",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(2,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles facil",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(3,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(10,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio una zapatilla",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(11,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles facil",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(12,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(13,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Instalacion de tomacorrientes",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(14,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio una tuberia en la cocina",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(15,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompieron las tuberias del baño",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(16,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio la caldera",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(17,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio la caldera denuevo",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE)),
            new JobContract(18,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio la caldera denuevo",new ByteImage(IMAGE_DATA, IMAGE_TYPE),new EncodedImage("image_data_for_testing",IMAGE_TYPE))
    };

    private static final Review[] REVIEWS = new Review[]{
            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[0],creationDate),
            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[1],creationDate),
            new Review(2,"Medio pelo","Resolvio todo de forma ideal",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[2],creationDate),
            new Review(2,"Medio pelo","Resolvio todo de forma ideal",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[3],creationDate),
            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[4],creationDate),
            new Review(2,"Medio pelo","Resolvio todo de forma ideal",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[5],creationDate),
            new Review(4,"Muy bueno","Resolvio todo en cuestion de minutos",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[6],creationDate),
            new Review(2,"Medio pelo","Resolvio todo de forma ideal",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[7],creationDate),

    };



    private static final int JOB_CONTRACTS_PRO1_QUANTITY = 12;

    private static final LocalDateTime date = LocalDateTime.now();


    @Autowired
    private DataSource ds;

    @InjectMocks
    @Autowired
    private ReviewDaoJDBC reviewDaoJDBC;

    @Test
    public void testCreate() {

        Review newReview = new Review(4,"Muy bueno!","Execelnte servicio",USER2,JOB_POSTS[0],JOB_CONTRACTS_PACKAGE1[11],LocalDateTime.now());

        Review maybeReview =reviewDaoJDBC.create(18,newReview.getRate(),newReview.getTitle(),newReview.getDescription());

        Assert.assertNotNull(maybeReview);
        Assert.assertEquals(newReview.getTitle(), maybeReview.getTitle());
        Assert.assertEquals(newReview.getDescription(), maybeReview.getDescription());
        Assert.assertEquals(newReview.getRate(), maybeReview.getRate());
        Assert.assertEquals(newReview.getClient(), maybeReview.getClient());
        Assert.assertEquals(newReview.getJobPost(), maybeReview.getJobPost());
        Assert.assertEquals(newReview.getJobContract(), maybeReview.getJobContract());
        Assert.assertEquals(newReview, maybeReview);
    }

    @Test
    public void testFindReviewsByPostId() {
        List<Review> maybePostReviews = reviewDaoJDBC.findReviewsByPostId(JOB_POSTS[0].getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(8, maybePostReviews.size());

        for (int i = 0; i < maybePostReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i].getTitle(), maybePostReviews.get(i).getTitle());
            Assert.assertEquals(REVIEWS[i].getDescription(), maybePostReviews.get(i).getDescription());
            Assert.assertEquals(REVIEWS[i].getRate(), maybePostReviews.get(i).getRate());
            Assert.assertEquals(REVIEWS[i].getClient(), maybePostReviews.get(i).getClient());
            Assert.assertEquals(REVIEWS[i].getJobPost(), maybePostReviews.get(i).getJobPost());
            Assert.assertEquals(REVIEWS[i].getJobContract(), maybePostReviews.get(i).getJobContract());
            Assert.assertEquals(REVIEWS[i], maybePostReviews.get(i));
        }
    }

    @Test
    public void testFindReviewsByPostIdSize() {
        List<Review> reviews = reviewDaoJDBC.findReviewsByPostId(JOB_POSTS[0].getId(), HirenetUtils.ALL_PAGES);

        int maybeReviewsByPostIdSize = reviewDaoJDBC.findJobPostReviewsSize(JOB_POSTS[0].getId());
        Assert.assertEquals(reviews.size(), maybeReviewsByPostIdSize);
    }

    @Test
    public void testFindJobPostAvgRate() {
        List<Review> reviews = reviewDaoJDBC.findReviewsByPostId(JOB_POSTS[0].getId(), HirenetUtils.ALL_PAGES);

        double maybeAvg = reviewDaoJDBC.findJobPostAvgRate(JOB_POSTS[0].getId());
        Assert.assertEquals(reviews.stream().mapToDouble(Review::getRate).average().orElse(0), maybeAvg, 0.0000001);
    }

    @Test
    public void findProfessionalReviews() {
        List<Review> maybeUserReviews = reviewDaoJDBC.findProfessionalReviews(USER1.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(8, maybeUserReviews.size());
        for (int i = 0; i < maybeUserReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i], maybeUserReviews.get(i));
        }
    }

    @Test
    public void testFindProfessionalAvgRate() {
        List<Review> reviews = reviewDaoJDBC.findProfessionalReviews(USER1.getId(), HirenetUtils.ALL_PAGES);

        double maybeAvg = reviewDaoJDBC.findProfessionalAvgRate(JOB_POSTS[0].getId());
        Assert.assertEquals(reviews.stream().mapToDouble(Review::getRate).average().orElse(0), maybeAvg, 0.0000001);
    }

    @Test
    public void testFindReviewsByPackageId() {
        List<Review> maybeReviews = reviewDaoJDBC.findReviewsByPackageId(JOB_PACKAGES[0].getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(8, maybeReviews.size());
        for (int i = 0; i < maybeReviews.size(); i++) {
            Assert.assertEquals(REVIEWS[i], maybeReviews.get(i));
        }

    }

    @Test
    public void testFindReviewByContractId() {
        Optional<Review> maybeReview = reviewDaoJDBC.findReviewByContractId(JOB_CONTRACTS_PACKAGE1[0].getId());

        Assert.assertTrue(maybeReview.isPresent());
        Assert.assertEquals(REVIEWS[0], maybeReview.get());
    }

}
