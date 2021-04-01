package jdbc;

import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.persistence.jdbc.JobContractDaoJDBC;
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
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job-contract-test.sql")
public class JobContractDaoJDBCTest {
    private static final long PROFESSIONAL_ID = 1;
    private static final long CLIENT_ID = 2;
    private static final long POST_ID = 1;
    private static final long PACKAGE_ID = 1;
    private static final long CONTRACT_ID = 1;
    private static final String DESCRIPTION = "Problema con la conexion de la luz";

    @Autowired
    private DataSource ds;

    @Autowired
    private JobContractDaoJDBC jobContractDaoJDBC;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreate() {
        JobContract jobContract = jobContractDaoJDBC.create(POST_ID,CLIENT_ID,PACKAGE_ID,DESCRIPTION);

        Assert.assertNotNull(jobContract);
        Assert.assertEquals(POST_ID,jobContract.getPostId());
        Assert.assertEquals(CLIENT_ID,jobContract.getClientId());
        Assert.assertEquals(DESCRIPTION,jobContract.getDescription());
        Assert.assertEquals(PACKAGE_ID,jobContract.getPackageId());

    }

    @Test
    public void testFindById(){
        Optional<JobContract> jobContract = jobContractDaoJDBC.findById(CONTRACT_ID);
        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(CONTRACT_ID,jobContract.get().getId());

    }

    @Test
    public void testFindByClientId(){
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByClientId(CLIENT_ID);

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        jobContracts.get().forEach(jobContract -> {
            Assert.assertEquals(CLIENT_ID,jobContract.getClientId());
        });
    }

    @Test
    public void testFindByProId(){
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByProId(PROFESSIONAL_ID);

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        jobContracts.get().forEach(jobContract -> {
//            Assert.assertEquals(PROFESSIONAL_ID,jobContract.getProfessionalId());
        });
    }

    @Test
    public void testFindByPostId(){
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByPostId(POST_ID);

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        jobContracts.get().forEach(jobContract -> {
            Assert.assertEquals(POST_ID,jobContract.getPostId());
        });
    }

}

