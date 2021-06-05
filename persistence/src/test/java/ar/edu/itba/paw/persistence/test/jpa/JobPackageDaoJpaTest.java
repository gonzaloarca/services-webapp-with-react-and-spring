package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.JobPackageDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
import ar.edu.itba.paw.models.exceptions.JobPostNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//TODO: renombrar a jpa
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job_package_data_test.sql")
@Transactional
public class JobPackageDaoJpaTest {

    private static final List<JobPost.Zone> ZONES =
            new ArrayList<JobPost.Zone>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));

    private static final User PROFESSIONAL = new User(
            1,
            "franquesada@mail.com",
            "Francisco Quesada",
            "11-3456-3232",
            true,
            true,
            LocalDateTime.now());

    private static final JobPost JOB_POSTS = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            true,
            LocalDateTime.now());

    private static final JobPackage[] JOB_PACKAGES = {
            new JobPackage(
                    1,
                    JOB_POSTS,
                    "Trabajo simple",
                    "Arreglo basico de electrodomesticos",
                    200.0,
                    JobPackage.RateType.values()[0],
                    true
            ), new JobPackage(
            2,
            JOB_POSTS,
            "Trabajo no tan simple",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            3,
            JOB_POSTS,
            "Trabajo simple 2",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            4,
            JOB_POSTS,
            "Trabajo no tan simple 2",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            5,
            JOB_POSTS,
            "Trabajo Complejo 2",
            "Arreglos de canerias",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            6,
            JOB_POSTS,
            "Trabajo barato 2",
            "Arreglos varios",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            7,
            JOB_POSTS,
            "Trabajo barato 2",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            8,
            JOB_POSTS,
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            9,
            JOB_POSTS,
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    )
    };

    //---------

    private static final User PROFESSIONAL_USER =
            new User(
                    1,
                    "franquesada@gmail.com",
                    "Francisco Quesada",
                    "1147895678",
                    true,
                    true,
                    LocalDateTime.now()
            );
    //JOB POST con id 1
    private static final JobPost JOB_POST = new JobPost(
            1,
            PROFESSIONAL_USER,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            true,
            LocalDateTime.now()
    );

    @InjectMocks
    @Autowired
    JobPackageDaoJpa jobPackageDaoJpa;

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

        JobPackage jobPackage = jobPackageDaoJpa.create(
                JOB_POSTS.getId(),
                title,
                description,
                price,
                rateType);

        Assert.assertNotNull(jobPackage);
        Assert.assertEquals(JOB_POSTS.getId(), jobPackage.getPostId());
        Assert.assertEquals(title, jobPackage.getTitle());
        Assert.assertEquals(description, jobPackage.getDescription());
        Assert.assertEquals(price, jobPackage.getPrice(), 0.001);
        Assert.assertEquals(rateType, jobPackage.getRateType());
    }

    @Test
    public void testFindById() {

        Optional<JobPackage> jobPackage = jobPackageDaoJpa.findById(JOB_PACKAGES[0].getId());

        Assert.assertTrue(jobPackage.isPresent());
        Assert.assertEquals(jobPackage.get(), JOB_PACKAGES[0]);
    }

    @Test
    public void testFindByPostIdWithoutPagination() {

        List<JobPackage> jobPackages = jobPackageDaoJpa.findByPostId(JOB_POSTS.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(JOB_PACKAGES.length, jobPackages.size());
        jobPackages.forEach((jobPackage) -> Assert.assertEquals(JOB_PACKAGES[jobPackages.indexOf(jobPackage)], jobPackage));
    }

    @Test
    public void testFindByPostIdWithPaginationFirstPage() {
        int page = 0;

        List<JobPackage> jobPackages = jobPackageDaoJpa.findByPostId(JOB_POSTS.getId(), page);

        Assert.assertEquals(HirenetUtils.PAGE_SIZE, jobPackages.size());
        jobPackages.forEach((jobPackage) -> Assert.assertEquals(JOB_PACKAGES[jobPackages.indexOf(jobPackage)], jobPackage));
    }

    @Test
    public void testFindByPostIdWithPaginationLastPage() {
        int page = 1;

        List<JobPackage> jobPackages = jobPackageDaoJpa.findByPostId(JOB_POSTS.getId(), page);

        Assert.assertEquals(1, jobPackages.size());
        jobPackages.forEach((jobPackage) -> Assert.assertEquals(JOB_PACKAGES[jobPackages.indexOf(jobPackage) + HirenetUtils.PAGE_SIZE * page], jobPackage));
    }

    @Test
    public void testEditPackage() {
        JobPackage jobPackage = JOB_PACKAGES[0];

        boolean ret = jobPackageDaoJpa.updatePackage(jobPackage.getId(), jobPackage.getTitle(), jobPackage.getDescription(), jobPackage.getPrice(), jobPackage.getRateType());

        Assert.assertTrue(ret);
    }

    @Test
    public void testDeletePackage() {
        JobPackage jobPackage = JOB_PACKAGES[0];

        boolean ret = jobPackageDaoJpa.deletePackage(jobPackage.getId());

        Assert.assertTrue(ret);
    }

    @Test(expected = JobPostNotFoundException.class)
    public void testCreatePackageWithNonExistingPost() {
        JobPackage jobPackage = JOB_PACKAGES[0];
        int postID = 999;

        jobPackageDaoJpa.create(postID, jobPackage.getTitle(), jobPackage.getDescription(), jobPackage.getPrice(), jobPackage.getRateType());
    }

    @Test
    public void testFindByIdNotFound() {
        int packageId = 999;

        Optional<JobPackage> maybePackage = jobPackageDaoJpa.findById(packageId);

        Assert.assertFalse(maybePackage.isPresent());
    }

    @Test
    public void testFindByPostIdNotExistsWithoutPagination() {
        int postId = 999;

        List<JobPackage> packages = jobPackageDaoJpa.findByPostId(postId, HirenetUtils.ALL_PAGES);

        Assert.assertTrue(packages.isEmpty());
    }

    @Test
    public void testFindByPostIdNotExistsWithPagination() {
        int postId = 999;

        List<JobPackage> packages = jobPackageDaoJpa.findByPostId(postId, 0);

        Assert.assertTrue(packages.isEmpty());
    }
}
