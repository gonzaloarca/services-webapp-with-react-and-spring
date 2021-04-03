package jdbc;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.persistence.jdbc.JobPostDaoJDBC;
import config.TestConfig;
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

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job-contract-test.sql")
public class JobPostDaoJDBCTest {

    private final long USER_ID = 1;
    private final long POST_ID = 1;
    private final String TITLE = "Plomeria De Buena calidad";
    private final String AVAILABLE_HOURS = "Lunes a viernes de 13 a 20";
    private final JobPost.JobType JOB_TYPE = JobPost.JobType.PLUMBING;
    private final List<JobPost.Zone> ZONES = new ArrayList<>(Arrays.asList(JobPost.Zone.BELGRANO, JobPost.Zone.PALERMO));

    @Autowired
    private DataSource ds;

    @Autowired
    private JobPostDaoJDBC jobPostDaoJDBC;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {
        JobPost jobPost = jobPostDaoJDBC.create(USER_ID,TITLE,AVAILABLE_HOURS,JOB_TYPE, ZONES);

        Assert.assertNotNull(jobPost);
        List<JobPost.Zone> zonesList = jobPost.getZones();
        Assert.assertEquals(USER_ID,jobPost.getUser());
        Assert.assertEquals(TITLE,jobPost.getTitle());
        Assert.assertEquals(AVAILABLE_HOURS,jobPost.getAvailableHours());
        Assert.assertEquals(JOB_TYPE,jobPost.getJobType());
        Assert.assertEquals(ZONES.size(),zonesList.size());
        int i = 0;
        for (JobPost.Zone zone : ZONES) {
            Assert.assertEquals(zone,zonesList.get(i++));
        }
    }

    @Test
    public void testFindById(){
        Optional<JobPost> jobPost = jobPostDaoJDBC.findById(POST_ID);

        Assert.assertTrue(jobPost.isPresent());
        System.out.println(jobPost);
        Assert.assertEquals(POST_ID,jobPost.get().getId());
    }
}
