package jdbc;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jdbc.JobPostDaoJDBC;
import ar.edu.itba.paw.persistence.jdbc.UserDaoJDBC;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
@Sql("classpath:job-post-test.sql")
public class JobPostDaoJDBCTest {
    private static final User USER = new User(1, "manurodriguez@gmail.com", "Manuel Rodriguez", "", "1109675432", false, true);
    private static final List<JobPost.Zone> ZONES = new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1], JobPost.Zone.values()[2]));
    private static final JobPost JOB_POST = new JobPost(1, USER, "Electricista Matriculado", "Lun a Viernes 10hs - 14hs", JobPost.JobType.values()[1], ZONES, true);
    private static final int JOB_POSTS_QUANTITY = 3;

    @Autowired
    private DataSource ds;

    @InjectMocks
    @Autowired
    private JobPostDaoJDBC jobPostDaoJDBC;

    private JdbcTemplate jdbcTemplate;

    @Mock
    private UserDaoJDBC mockUserDao;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Mockito.when(mockUserDao.findById(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(USER));


        JobPost jobPost = jobPostDaoJDBC.create(USER.getId(), JOB_POST.getTitle(), JOB_POST.getAvailableHours(),
                JOB_POST.getJobType(), JOB_POST.getZones());

        Assert.assertNotNull(jobPost);
        List<JobPost.Zone> zonesList = jobPost.getZones();
        Assert.assertEquals(JOB_POST.getUser(), jobPost.getUser());
        Assert.assertEquals(JOB_POST.getTitle(), jobPost.getTitle());
        Assert.assertEquals(JOB_POST.getAvailableHours(), jobPost.getAvailableHours());
        Assert.assertEquals(JOB_POST.getJobType(), jobPost.getJobType());
        Assert.assertEquals(JOB_POST.getZones().size(), zonesList.size());
        int i = 0;
        for (JobPost.Zone zone : JOB_POST.getZones()) {
            Assert.assertEquals(zone, zonesList.get(i++));
        }
    }

    @Test
    public void testFindById() {
        Optional<JobPost> jobPost = jobPostDaoJDBC.findById(JOB_POST.getId());

        Assert.assertTrue(jobPost.isPresent());
        System.out.println(jobPost);
        Assert.assertEquals(JOB_POST, jobPost.get());
    }


    @Test
    public void findByUserId() {
        Optional<List<JobPost>> jobPosts = jobPostDaoJDBC.findByUserId(JOB_POST.getUser().getId());

        Assert.assertTrue(jobPosts.isPresent());
        Assert.assertEquals(jobPosts.get().size(), 2);
        jobPosts.get().forEach((jobPost -> Assert.assertEquals(JOB_POST.getUser(), jobPost.getUser())));
    }

    @Test
    public void findByJobType() {
        Optional<List<JobPost>> jobPosts = jobPostDaoJDBC.findByJobType(JOB_POST.getJobType());

        Assert.assertTrue(jobPosts.isPresent());
        Assert.assertEquals(jobPosts.get().size(), 1);
        jobPosts.get().forEach((jobPost -> Assert.assertEquals(JOB_POST.getJobType(), jobPost.getJobType())));
    }

    @Test
    public void findByZone() {
        JOB_POST.getZones().forEach((zone -> {
            Optional<List<JobPost>> jobPosts = jobPostDaoJDBC.findByZone(zone);

            Assert.assertTrue(jobPosts.isPresent());
            jobPosts.get().forEach((jobPost -> Assert.assertTrue(jobPost.getZones().contains(zone))));
        }));
    }

    @Test
    public void findAll() {
        Optional<List<JobPost>> jobPosts = jobPostDaoJDBC.findAll();

        Assert.assertTrue(jobPosts.isPresent());
        Assert.assertEquals(JOB_POSTS_QUANTITY,
                jobPosts.get().size());
    }

    @Test
    public void search() {
        String title = "Electricista";
        JobPost.Zone zone = JobPost.Zone.PALERMO;
        Optional<List<JobPost>> jobPosts = jobPostDaoJDBC.search(title, zone);

        Assert.assertTrue(jobPosts.isPresent());
        Assert.assertFalse(jobPosts.get().isEmpty());
        Assert.assertEquals(2, jobPosts.get().size());
        System.out.println(jobPosts.get());
    }

    @Test
    public void searchWithCategory() {
        String title = "";
        JobPost.Zone zone = JobPost.Zone.PALERMO;
        JobPost.JobType jobType = JobPost.JobType.ELECTRICITY;
        Optional<List<JobPost>> jobPosts = jobPostDaoJDBC.searchWithCategory(title, zone, jobType);

        Assert.assertTrue(jobPosts.isPresent());
        Assert.assertFalse(jobPosts.get().isEmpty());
        Assert.assertEquals(2, jobPosts.get().size());
        System.out.println(jobPosts.get());
    }
}
