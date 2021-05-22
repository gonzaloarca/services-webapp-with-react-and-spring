package jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostZone;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.JobPostDaoJpa;
import ar.edu.itba.paw.persistence.jpa.UserDaoJpa;
import config.TestConfig;
import exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:job_post_data_test.sql")
public class JobPostDaoJDBCTest {

    private static final long JOB_POST_COUNT = 10;
    private static final long ACTIVE_JOB_POST_COUNT = 9;
    private static final int ACTIVE_JOB_POSTS_COUNT_FOR_USER1 = 9;
    private static final int FIRST_PAGE_JOB_POST_COUNT = 8;
    private static final int LAST_PAGE_JOB_POST_COUNT = 1;
    private static final int FIRST_PAGE_JOB_POST_USER1_COUNT = 8;
    private static final int LAST_PAGE_JOB_POST_USER1_COUNT = 1;
    private static final int JOB_POSTS_OF_TYPE_1_COUNT = 3;
    private static final int ACTIVE_ZONE1_COUNT = 9;
    private static final int ACTIVE_ZONE1_PAGE1_COUNT = 8;
    private static final int NONEXISTENT_ID = 999;
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
    private static final List<JobPostZone> ZONES =
            new ArrayList<>(Arrays.asList(new JobPostZone(JobPostZone.Zone.values()[1]),
                    new JobPostZone(JobPostZone.Zone.values()[2])));
    private static final JobPost[] JOB_POSTS = new JobPost[]{
            new JobPost(
                    1,
                    PROFESSIONAL_USER,
                    "Electricista Matriculado",
                    "Lun a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    2,
                    PROFESSIONAL_USER,
                    "Paseador de perros",
                    "Viernes a sabados 09hs - 14hs",
                    JobPost.JobType.values()[3],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    3,
                    PROFESSIONAL_USER,
                    "Electricista no matriculado",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    4,
                    PROFESSIONAL_USER,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    5,
                    PROFESSIONAL_USER,
                    "Electricista Matriculado 2",
                    "Lun a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    6,
                    PROFESSIONAL_USER,
                    "Paseador de perros 2",
                    "Viernes a sabados 09hs - 14hs",
                    JobPost.JobType.values()[3],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    7,
                    PROFESSIONAL_USER,
                    "Electricista no matriculado 2",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    8,
                    PROFESSIONAL_USER,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    9,
                    PROFESSIONAL_USER,
                    "Plomero Matriculado 3",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,
                    true,
                    LocalDateTime.now()
            )};
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
    private static final JobPost INACTIVE_JOB_POST = new JobPost(
            10,
            PROFESSIONAL_USER,
            "Plomero Inactivo",
            "Miercoles a Viernes 10hs - 14hs",
            JobPost.JobType.values()[2],
            ZONES,
            true,
            LocalDateTime.now()
    );

    @Autowired
    private DataSource ds;

    @InjectMocks
    @Autowired
    private JobPostDaoJpa jobPostDaoJpa;

    private JdbcTemplate jdbcTemplate;

    @Mock
    private UserDaoJpa mockUserDao;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        String title = "Mi posteo";
        String availableHours = "Todos los dias";
        JobPost.JobType jobType = JobPost.JobType.PAINTING;
        List<JobPostZone> zones = Arrays.asList(new JobPostZone(JobPostZone.Zone.ALMAGRO), new JobPostZone(JobPostZone.Zone.AGRONOMIA));
        JobPost testCreateJobPost = new JobPost(JOB_POST_COUNT + 1, PROFESSIONAL_USER, title, availableHours,
                jobType, zones, LocalDateTime.now());
        Mockito.when(mockUserDao.findById(Mockito.eq(PROFESSIONAL_USER.getId())))
                .thenReturn(Optional.of(PROFESSIONAL_USER));


        JobPost jobPost = jobPostDaoJpa.create(PROFESSIONAL_USER.getId(), testCreateJobPost.getTitle(), testCreateJobPost.getAvailableHours(),
                testCreateJobPost.getJobType(), testCreateJobPost.getZones());
        em.flush();
        //TODO: BUSCARLO EN LA BASE DE DATOS Y VERIFICAR LA CREACION?
        Assert.assertNotNull(jobPost);
        List<JobPostZone> zonesList = jobPost.getZones();
        Assert.assertEquals(testCreateJobPost.getUser(), jobPost.getUser());
        Assert.assertEquals(testCreateJobPost.getTitle(), jobPost.getTitle());
        Assert.assertEquals(testCreateJobPost.getAvailableHours(), jobPost.getAvailableHours());
        Assert.assertEquals(testCreateJobPost.getJobType(), jobPost.getJobType());
        Assert.assertEquals(testCreateJobPost.getZones().size(), zonesList.size());
        int i = 0;
        for (JobPostZone zone : testCreateJobPost.getZones()) {
            Assert.assertEquals(zone, zonesList.get(i++));
        }
    }

    @Test
    public void testFindById() {

        Optional<JobPost> jobPost = jobPostDaoJpa.findById(JOB_POST.getId());

        Assert.assertTrue(jobPost.isPresent());
        Assert.assertEquals(JOB_POST, jobPost.get());
    }


    @Test
    public void testFindByUserIdWithoutPagination() {

        List<JobPost> jobPosts = jobPostDaoJpa.findByUserId(PROFESSIONAL_USER.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(ACTIVE_JOB_POSTS_COUNT_FOR_USER1, jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POST.getUser(), jobPost.getUser())));
    }

    @Test
    public void testFindByUserIdWithPagination() {

        List<JobPost> jobPosts = jobPostDaoJpa.findByUserId(PROFESSIONAL_USER.getId(), 0);

        Assert.assertEquals(FIRST_PAGE_JOB_POST_USER1_COUNT, jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POST.getUser(), jobPost.getUser())));
    }

    @Test
    public void testFindByUserIdWithPaginationLastPage() {

        List<JobPost> jobPosts = jobPostDaoJpa.findByUserId(JOB_POST.getUser().getId(), 1);

        Assert.assertEquals(LAST_PAGE_JOB_POST_USER1_COUNT, jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POST.getUser(), jobPost.getUser())));
    }

    @Test
    public void testFindSizeByUserId() {

        int maybeSize = jobPostDaoJpa.findSizeByUserId(PROFESSIONAL_USER.getId());

        Assert.assertEquals(ACTIVE_JOB_POSTS_COUNT_FOR_USER1, maybeSize);
    }

    @Test
    public void testFindByJobTypeWithoutPagination() {

        List<JobPost> jobPosts = jobPostDaoJpa.findByJobType(JOB_POST.getJobType(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(JOB_POSTS_OF_TYPE_1_COUNT, jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POST.getJobType(), jobPost.getJobType())));
    }

    @Test
    public void testFindByJobTypeWithPagination() {

        List<JobPost> jobPosts = jobPostDaoJpa.findByJobType(JOB_POST.getJobType(), 0);

        Assert.assertEquals(JOB_POSTS_OF_TYPE_1_COUNT, jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POST.getJobType(), jobPost.getJobType())));
    }

    @Test
    public void testFindByZoneWithoutPagination() {

        JobPostZone zone = new JobPostZone(JobPostZone.Zone.values()[1]);
        List<JobPost> jobPosts = jobPostDaoJpa.findByZone(zone.getZone(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(ACTIVE_ZONE1_COUNT, jobPosts.size());
        jobPosts.forEach(jobPost -> Assert.assertTrue(jobPost.getZones().contains(zone)));
    }

    @Test
    public void testFindByZoneWithPagination() {

        JobPostZone zone = new JobPostZone(JobPostZone.Zone.values()[1]);
        List<JobPost> jobPosts = jobPostDaoJpa.findByZone(zone.getZone(), 0);

        Assert.assertEquals(ACTIVE_ZONE1_PAGE1_COUNT, jobPosts.size());
        jobPosts.forEach(jobPost -> Assert.assertTrue(jobPost.getZones().contains(zone)));
    }

    @Test
    public void testFindAllWithoutPagination() {

        List<JobPost> jobPosts = jobPostDaoJpa.findAll(HirenetUtils.ALL_PAGES);

        Assert.assertEquals(ACTIVE_JOB_POST_COUNT,
                jobPosts.size());
        int i = 0;
        for (JobPost jobPost :
                jobPosts) {
            Assert.assertEquals(JOB_POSTS[i++], jobPost);
        }
    }

    @Test
    public void testFindAllWithPagination() {

        List<JobPost> jobPosts = jobPostDaoJpa.findAll(0);

        Assert.assertEquals(FIRST_PAGE_JOB_POST_COUNT, jobPosts.size());
        int i = 0;
        for (JobPost jobPost :
                jobPosts) {
            Assert.assertEquals(JOB_POSTS[i++], jobPost);
        }
    }

    @Test
    public void testFindAllWithPaginationLastPage() {

        List<JobPost> jobPosts = jobPostDaoJpa.findAll(1);

        Assert.assertEquals(LAST_PAGE_JOB_POST_COUNT, jobPosts.size());
        Assert.assertEquals(JOB_POSTS[JOB_POSTS.length - 1], jobPosts.get(0));

    }

    @Test
    public void testUpdateJobPost() {

        boolean status = jobPostDaoJpa.updateById(JOB_POST.getId(), "OTRO TITULO",
                "OTRO AVAILABLE HOURS", JOB_POST.getJobType(), JOB_POST.getZones());

        Assert.assertTrue(status);
    }

    @Test
    public void testDeleteJobPost() {

        boolean status = jobPostDaoJpa.deleteJobPost(JOB_POST.getId());

        Assert.assertTrue(status);
    }

    @Test
    public void testUpdateNonexistentPost() {

        boolean status = jobPostDaoJpa.updateById(NONEXISTENT_ID, "", "", JobPost.JobType.BABYSITTING,
                Arrays.asList(new JobPostZone(JobPostZone.Zone.values()[0]), new JobPostZone(JobPostZone.Zone.values()[1])));

        Assert.assertFalse(status);
    }

    @Test
    public void testDeleteNonexistentPost() {

        boolean status = jobPostDaoJpa.deleteJobPost(NONEXISTENT_ID);

        Assert.assertFalse(status);
    }

    @Test
    public void testFindJobPostByIdAndInactive() {

        Optional<JobPost> jobPost = jobPostDaoJpa.findByIdWithInactive(INACTIVE_JOB_POST.getId());

        Assert.assertTrue(jobPost.isPresent());
        Assert.assertEquals(INACTIVE_JOB_POST, jobPost.get());
    }

    @Test
    public void testFindJobPostByIdAndInactiveNonexistent() {

        Optional<JobPost> jobPost = jobPostDaoJpa.findByIdWithInactive(NONEXISTENT_ID);

        Assert.assertFalse(jobPost.isPresent());
    }

    @Test
    public void testFindJobPostByIdButIsInactive() {

        Optional<JobPost> jobPost = jobPostDaoJpa.findById(INACTIVE_JOB_POST.getId());

        Assert.assertFalse(jobPost.isPresent());
    }

    @Test(expected = UserNotFoundException.class)
    public void testCreateJobPostFromNonexistentUser() {

        jobPostDaoJpa.create(NONEXISTENT_ID, JOB_POST.getTitle(), JOB_POST.getAvailableHours(), JOB_POST.getJobType(),
                JOB_POST.getZones());

    }
}
