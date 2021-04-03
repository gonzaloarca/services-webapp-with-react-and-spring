package jdbc;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jdbc.JobContractDaoJDBC;
import ar.edu.itba.paw.persistence.jdbc.JobPackageDaoJDBC;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.*;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job-contract-test.sql")
public class JobContractDaoJDBCTest {

    private static final User PROFESSIONAL = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "",
            "1147895678",
            true,
            true
    );
    private static final User CLIENT = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "",
            "1109675432",
            false,
            true
    );
    private static final List<JobPost.Zone> ZONES = new ArrayList<>(
            Arrays.asList(
                    JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]
            )
    );
    private static final JobPost JOB_POST = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            true
    );
    private static final JobPackage JOB_PACKAGE = new JobPackage(
            1,
            1,
            "Trabajo Simple",
            "Arreglos de tomacorrientes",
            200.00, JobPackage.RateType.values()[0],
            true
    );
    private static final JobContract JOB_CONTRACT = new JobContract(
            1, CLIENT,
            JOB_PACKAGE,
            PROFESSIONAL,
            new Date(),
            "Se me rompio una zapatilla",
            ""
    );

    private static final String DESCRIPTION = "Se me rompio la toma de corriente";
    @Autowired
    private DataSource ds;

    @Mock
    private JobPostDaoJDBC mockPostDao;

    @Mock
    private UserDaoJDBC mockUserDao;

    @Mock
    private JobPackageDaoJDBC mockJobPackageDao;

    @InjectMocks
    @Autowired
    private JobContractDaoJDBC jobContractDaoJDBC;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {

        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {

        Mockito.when(mockPostDao.findById(Mockito.eq(JOB_POST.getId())))
                .thenReturn(Optional.of(JOB_POST));
        Mockito.when(mockUserDao.findById(Mockito.eq(CLIENT.getId())))
                .thenReturn(Optional.of(CLIENT));
        Mockito.when(mockUserDao.findById(Mockito.eq(PROFESSIONAL.getId())))
                .thenReturn(Optional.of(PROFESSIONAL));
        Mockito.when(mockJobPackageDao.findById(JOB_PACKAGE.getId()))
                .thenReturn(Optional.of(JOB_PACKAGE));

        JobContract jobContract = jobContractDaoJDBC.create(CLIENT.getId(), JOB_PACKAGE.getId(), DESCRIPTION);

        Assert.assertNotNull(jobContract);

        Assert.assertEquals(CLIENT, jobContract.getClient());
        Assert.assertEquals(CLIENT.getUsername(), jobContract.getClient().getUsername());
        Assert.assertEquals(CLIENT.getPhone(), jobContract.getClient().getPhone());
        Assert.assertEquals(CLIENT.getUserImage(), jobContract.getClient().getUserImage());
        Assert.assertEquals(CLIENT.isActive(), jobContract.getClient().isActive());

        Assert.assertEquals(PROFESSIONAL, jobContract.getProfessional());
        Assert.assertEquals(PROFESSIONAL.getEmail(), jobContract.getProfessional().getEmail());
        Assert.assertEquals(PROFESSIONAL.getUsername(), jobContract.getProfessional().getUsername());
        Assert.assertEquals(PROFESSIONAL.getPhone(), jobContract.getProfessional().getPhone());
        Assert.assertEquals(PROFESSIONAL.getUserImage(), jobContract.getProfessional().getUserImage());
        Assert.assertEquals(PROFESSIONAL.isActive(), jobContract.getProfessional().isActive());

        Assert.assertEquals(JOB_PACKAGE, jobContract.getJobPackage());
        Assert.assertEquals(JOB_PACKAGE.getPostId(), jobContract.getJobPackage().getPostId());
        Assert.assertEquals(JOB_PACKAGE.getDescription(), jobContract.getJobPackage().getDescription());
        Assert.assertEquals(JOB_PACKAGE.getTitle(), jobContract.getJobPackage().getTitle());
        Assert.assertEquals(JOB_PACKAGE.getPrice(), jobContract.getJobPackage().getPrice(), 0.001);
        Assert.assertEquals(JOB_PACKAGE.getRateType(), jobContract.getJobPackage().getRateType());
        Assert.assertEquals(JOB_PACKAGE.is_active(), jobContract.getJobPackage().is_active());

        Assert.assertNotNull(jobContract.getCreationDate());
        Assert.assertEquals(JOB_CONTRACT.getId() + 1, jobContract.getId());
        Assert.assertEquals(DESCRIPTION, jobContract.getDescription());
    }

    @Test
    public void testFindById() {
        Optional<JobContract> jobContract = jobContractDaoJDBC.findById(JOB_CONTRACT.getId());
        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(JOB_CONTRACT.getId(), jobContract.get().getId());
    }


    @Test
    public void testFindByClientId() {
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByClientId(CLIENT.getId());

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        System.out.println(jobContracts.get());
        jobContracts.get().forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByProId() {
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByProId(PROFESSIONAL.getId());

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        jobContracts.get().forEach(jobContract -> {
            Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId());
        });
    }

    @Test
    public void testFindByPackageId() {
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByPackageId(JOB_CONTRACT.getJobPackage().getId());

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        Assert.assertEquals(1, jobContracts.get().size());
        Assert.assertEquals(JOB_PACKAGE.getId(), jobContracts.get().get(0).getJobPackage().getId());
    }

    @Test
    public void testFindByPostId() {
        Optional<List<JobContract>> jobContracts = jobContractDaoJDBC.findByPostId(JOB_CONTRACT.getJobPackage().getPostId());

        Assert.assertTrue(jobContracts.isPresent());
        Assert.assertFalse(jobContracts.get().isEmpty());
        Assert.assertEquals(1, jobContracts.get().size());
        Assert.assertEquals(JOB_POST.getId(), jobContracts.get().get(0).getJobPackage().getPostId());
    }

}

