package jdbc;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job_contract_test.sql")
public class JobContractDaoJDBCTest {

    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true
    );
    private static final List<UserAuth.Role> USER1_ROLES = Arrays.asList(UserAuth.Role.CLIENT, UserAuth.Role.PROFESSIONAL);
    private static final User USER2 = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true
    );
    private static final List<UserAuth.Role> USER2_ROLES = Arrays.asList(UserAuth.Role.CLIENT);

    private static final List<JobPost.Zone> ZONES = new ArrayList<>(
            Arrays.asList(
                    JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]
            )
    );
    private static final JobPost JOB_POST = new JobPost(
            1,
            USER2,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            0.0,
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
            1, USER2,
            JOB_PACKAGE,
            USER1,
            new Date(),
            "Se me rompio una zapatilla"
    );

    private static final String DESCRIPTION = "Se me rompio la toma de corriente";

    private static final byte[] IMAGE_DATA = "image_data_for_testing".getBytes(StandardCharsets.UTF_8);

    private static final String IMAGE_TYPE = "image/jpg";

    private static final int JOB_CONTRACTS_PRO_QUANTITY = 3;

    private static final int JOB_CONTRACTS_TOTAL_QUANTITY = JOB_CONTRACTS_PRO_QUANTITY + 2;

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
        Mockito.when(mockUserDao.findById(Mockito.eq(USER2.getId())))
                .thenReturn(Optional.of(USER2));
        Mockito.when(mockUserDao.findById(Mockito.eq(USER1.getId())))
                .thenReturn(Optional.of(USER1));
        Mockito.when(mockJobPackageDao.findById(JOB_PACKAGE.getId()))
                .thenReturn(Optional.of(JOB_PACKAGE));

        JobContract jobContract = jobContractDaoJDBC.create(USER1.getId(), JOB_PACKAGE.getId(), DESCRIPTION,
                new ByteImage(IMAGE_DATA, IMAGE_TYPE));

        Assert.assertNotNull(jobContract);

        Assert.assertEquals(USER1, jobContract.getClient());
        Assert.assertEquals(USER1.getUsername(), jobContract.getClient().getUsername());
        Assert.assertEquals(USER1.getPhone(), jobContract.getClient().getPhone());
//        Assert.assertEquals(USER2.getUserImage(), jobContract.getClient().getUserImage());
        Assert.assertEquals(USER1.isActive(), jobContract.getClient().isActive());

        Assert.assertEquals(USER2, jobContract.getProfessional());
        Assert.assertEquals(USER2.getEmail(), jobContract.getProfessional().getEmail());
        Assert.assertEquals(USER2.getUsername(), jobContract.getProfessional().getUsername());
        Assert.assertEquals(USER2.getPhone(), jobContract.getProfessional().getPhone());
//        Assert.assertEquals(USER1.getUserImage(), jobContract.getProfessional().getUserImage());
        Assert.assertEquals(USER2.isActive(), jobContract.getProfessional().isActive());

        Assert.assertEquals(JOB_PACKAGE, jobContract.getJobPackage());
        Assert.assertEquals(JOB_PACKAGE.getPostId(), jobContract.getJobPackage().getPostId());
        Assert.assertEquals(JOB_PACKAGE.getDescription(), jobContract.getJobPackage().getDescription());
        Assert.assertEquals(JOB_PACKAGE.getTitle(), jobContract.getJobPackage().getTitle());
        Assert.assertEquals(JOB_PACKAGE.getPrice(), jobContract.getJobPackage().getPrice(), 0.001);
        Assert.assertEquals(JOB_PACKAGE.getRateType(), jobContract.getJobPackage().getRateType());
        Assert.assertEquals(JOB_PACKAGE.is_active(), jobContract.getJobPackage().is_active());

        Assert.assertNotNull(jobContract.getCreationDate());
        Assert.assertEquals(JOB_CONTRACT.getId() + JOB_CONTRACTS_TOTAL_QUANTITY, jobContract.getId());
        Assert.assertEquals(DESCRIPTION, jobContract.getDescription());
//        Assert.assertEquals(IMAGE_DATA, jobContract.getImageData());
    }

    @Test
    public void testFindById() {
        Optional<JobContract> jobContract = jobContractDaoJDBC.findById(JOB_CONTRACT.getId());
        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(JOB_CONTRACT.getId(), jobContract.get().getId());
//        Assert.assertEquals(JOB_CONTRACT.getImageData(), jobContract.get().getImageData());
    }

    @Test
    public void testFindByClientId() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByClientId(USER2.getId(),0);

        Assert.assertFalse(jobContracts.isEmpty());
        System.out.println(jobContracts);
        jobContracts.forEach(jobContract -> Assert.assertEquals(USER2.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByProId() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByProId(USER1.getId(),0);

        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(USER1.getId(), jobContract.getProfessional().getId()));
    }
//      TODO: FIX tests
//    @Test
//    public void testFindByPackageId() {
//        List<JobContract> jobContracts = jobContractDaoJDBC.findByPackageId(JOB_CONTRACT.getJobPackage().getId(),0);
//
//        Assert.assertFalse(jobContracts.isEmpty());
//        Assert.assertEquals(2, jobContracts.size());
//        Assert.assertEquals(JOB_PACKAGE.getId(), jobContracts.get(0).getJobPackage().getId());
//    }
//
//    @Test
//    public void testFindByPostId() {
//        List<JobContract> jobContracts = jobContractDaoJDBC.findByPostId(JOB_CONTRACT.getJobPackage().getPostId(),0);
//
//        Assert.assertFalse(jobContracts.isEmpty());
//        Assert.assertEquals(3, jobContracts.size());  // dos son del package 1 y otro es del package 2
//        jobContracts.forEach(jobContract ->
//                Assert.assertEquals(JOB_POST.getId(), jobContract.getJobPackage().getPostId()));
//    }

    @Test
    public void testFindContractsQuantityByProId() {
        int ans = jobContractDaoJDBC.findContractsQuantityByProId(USER1.getId());

        Assert.assertEquals(JOB_CONTRACTS_PRO_QUANTITY, ans);
    }

    @Test
    public void testFindContractsQuantityByNotExistingProId() {
        int ans = jobContractDaoJDBC.findContractsQuantityByProId(USER1.getId() + 100);

        Assert.assertEquals(0, ans);
    }

}
