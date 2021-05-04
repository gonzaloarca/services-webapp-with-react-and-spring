package jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO hacer tests para JobCardDao

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:db_data_test.sql")
public class JobPostDaoJDBCTest {
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
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    4,
                    USER1,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    5,
                    USER1,
                    "Electricista Matriculado 2",
                    "Lun a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    6,
                    USER1,
                    "Paseador de perros 2",
                    "Viernes a sabados 09hs - 14hs",
                    JobPost.JobType.values()[3],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    7,
                    USER1,
                    "Electricista no matriculado 2",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    8,
                    USER1,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    9,
                    USER1,
                    "Plomero Matriculado 3",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    10,
                    USER1,
                    "Plomero Matriculado 4",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES,0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(3,USER2,"Electricista no matriculado","Lun a Jueves 13hs - 14hs",JobPost.JobType.values()[1],ZONES,0.0,true,LocalDateTime.now()
                    )};
    private static final int JOB_POSTS_QUANTITY = 10;

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
        Mockito.when(mockUserDao.findById(Mockito.eq(USER1.getId())))
                .thenReturn(Optional.of(USER1));

        JobPost jobPost = jobPostDaoJDBC.create(USER1.getId(), JOB_POSTS[0].getTitle(), JOB_POSTS[0].getAvailableHours(),
                JOB_POSTS[0].getJobType(), JOB_POSTS[0].getZones());

        Assert.assertNotNull(jobPost);
        List<JobPost.Zone> zonesList = jobPost.getZones();
        Assert.assertEquals(JOB_POSTS[0].getUser(), jobPost.getUser());
        Assert.assertEquals(JOB_POSTS[0].getTitle(), jobPost.getTitle());
        Assert.assertEquals(JOB_POSTS[0].getAvailableHours(), jobPost.getAvailableHours());
        Assert.assertEquals(JOB_POSTS[0].getJobType(), jobPost.getJobType());
        Assert.assertEquals(JOB_POSTS[0].getZones().size(), zonesList.size());
        int i = 0;
        for (JobPost.Zone zone : JOB_POSTS[0].getZones()) {
            Assert.assertEquals(zone, zonesList.get(i++));
        }
    }

    @Test
    public void testFindById() {
        Optional<JobPost> jobPost = jobPostDaoJDBC.findById(JOB_POSTS[0].getId());

        Assert.assertTrue(jobPost.isPresent());
        System.out.println(jobPost);
        Assert.assertEquals(JOB_POSTS[0], jobPost.get());
    }


    @Test
    public void testFindByUserId() {
        List<JobPost> jobPosts = jobPostDaoJDBC.findByUserId(JOB_POSTS[0].getUser().getId(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(JOB_POSTS_QUANTITY, jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POSTS[0].getUser(), jobPost.getUser())));
    }

    @Test
    public void testFindSizeByUserId() {
        int size = jobPostDaoJDBC.findByUserId(JOB_POSTS[0].getUser().getId(), HirenetUtils.ALL_PAGES).size();

        int maybeSize = jobPostDaoJDBC.findSizeByUserId(JOB_POSTS[0].getUser().getId());

        Assert.assertEquals(size, maybeSize);
    }

    @Test
    public void testFindByJobType() {
        List<JobPost> jobPosts = jobPostDaoJDBC.findByJobType(JOB_POSTS[0].getJobType(), HirenetUtils.ALL_PAGES);

        Assert.assertEquals(Arrays.stream(JOB_POSTS).filter(post -> post.getJobType().equals(JOB_POSTS[0].getJobType())).count(), jobPosts.size());
        jobPosts.forEach((jobPost -> Assert.assertEquals(JOB_POSTS[0].getJobType(), jobPost.getJobType())));
    }

    @Test
    public void testFindByZone() {
        JOB_POSTS[0].getZones().forEach((zone -> {
            List<JobPost> jobPosts = jobPostDaoJDBC.findByZone(zone, HirenetUtils.ALL_PAGES);

            jobPosts.forEach((jobPost -> Assert.assertTrue(jobPost.getZones().contains(zone))));
        }));
    }

    @Test
    public void testFindAll() {
        List<JobPost> jobPosts = jobPostDaoJDBC.findAll(HirenetUtils.ALL_PAGES);

        Assert.assertEquals(JOB_POSTS_QUANTITY,
                jobPosts.size());
    }

}
