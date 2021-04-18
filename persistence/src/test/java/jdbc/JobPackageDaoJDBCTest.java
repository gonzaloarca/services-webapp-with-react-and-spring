package jdbc;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jdbc.JobPackageDaoJDBC;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job-package-test.sql")
public class JobPackageDaoJDBCTest {

    private static final List<JobPost.Zone> ZONES = new ArrayList<>(
            Arrays.asList(
                    JobPost.Zone.values()[0],
                    JobPost.Zone.values()[1]
            )
    );

    private static final User PROFESSIONAL = new User(
            1,
            "franquesada@mail.com",
            "Francisco Quesada",
            "",
            "11-3456-3232",
            true,
            true
    );


    private static final JobPost JOB_POST = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista de primera mano",
            "Lunes a viernes 10 a 20",
            JobPost.JobType.ELECTRICITY,
            ZONES,
            0.0,
            true);

    private static final JobPackage[] JOB_PACKAGES = {new JobPackage(
            1,
            JOB_POST.getId(),
            "Trabajo simple",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            2,
            JOB_POST.getId(),
            "Trabajo Largo",
            "Arreglo De toda el sistema electrico",
            1000.0,
            JobPackage.RateType.values()[2],
            true
    )};
    private static final Review REVIEW_1 = new Review(
            4,
            "Muy bueno",
            "Resolvio todo en cuestion de minutos"
    );
    private static final Review REVIEW_2 = new Review(
            2, "Medio pelo", "Resolvio todo de forma ideal"
    );

    @InjectMocks
    @Autowired
    JobPackageDaoJDBC jobPackageDaojdbc;

    @Autowired
    DataSource ds;

    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {

        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {
        String title = "Arreglo complejo";
        String description = "Arreglo de instalaciones electricas de 1 fase";
        double price = 500.0;
        JobPackage.RateType rateType = JobPackage.RateType.ONE_TIME;
        JobPackage jobPackage = jobPackageDaojdbc.create(
                JOB_POST.getId(),
                title,
                description,
                price,
                rateType);

        Assert.assertNotNull(jobPackage);
        Assert.assertEquals(JOB_POST.getId(), jobPackage.getPostId());
        Assert.assertEquals(title, jobPackage.getTitle());
        Assert.assertEquals(description, jobPackage.getDescription());
        Assert.assertEquals(price, jobPackage.getPrice(), 0.001);
        Assert.assertEquals(rateType, jobPackage.getRateType());
    }

    @Test
    public void testFindById() {
        Optional<JobPackage> jobPackage = jobPackageDaojdbc.findById(JOB_PACKAGES[0].getId());

        Assert.assertTrue(jobPackage.isPresent());
        Assert.assertEquals(jobPackage.get(), JOB_PACKAGES[0]);
    }

    @Test
    public void testFindByPostId() {
        List<JobPackage> jobPackages = jobPackageDaojdbc.findByPostId(JOB_POST.getId());
        Assert.assertEquals(jobPackages.size(), JOB_PACKAGES.length);
        jobPackages.forEach((jobPackage) -> Assert.assertEquals(JOB_PACKAGES[jobPackages.indexOf(jobPackage)], jobPackage));
    }

    @Test
    public void testFindReviews() {
        List<Review> maybeReviews = jobPackageDaojdbc.findReviews(JOB_PACKAGES[0].getId());

        Assert.assertEquals(maybeReviews.size(), 2);
        Assert.assertEquals(maybeReviews.get(0), REVIEW_1);
        Assert.assertEquals(maybeReviews.get(1), REVIEW_2);
    }
}
