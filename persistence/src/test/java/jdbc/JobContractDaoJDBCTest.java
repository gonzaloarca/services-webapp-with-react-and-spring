package jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job_contract_data_test.sql")
public class JobContractDaoJDBCTest {
    private static final User PROFESSIONAL = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());
    private static final User CLIENT = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true,
            LocalDateTime.now());
    private static final List<JobPost.Zone> ZONES = new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1], JobPost.Zone.values()[2]));
    private static final JobPost JOB_POST = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES, 0.0,
            true,
            LocalDateTime.now()
    );

    private static final JobPackage[] JOB_PACKAGES = {
            new JobPackage(
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
            "Trabajo no tan simple",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            3,
            JOB_POST.getId(),
            "Trabajo simple 2",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            4,
            JOB_POST.getId(),
            "Trabajo no tan simple 2",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            5,
            JOB_POST.getId(),
            "Trabajo Complejo 2",
            "Arreglos de canerias",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            6,
            JOB_POST.getId(),
            "Trabajo barato 2",
            "Arreglos varios",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            7,
            JOB_POST.getId(),
            "Trabajo barato 2",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            8,
            JOB_POST.getId(),
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            9,
            JOB_POST.getId(),
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
            new JobContract(1, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio una zapatilla", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(2, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles facil", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(3, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(4, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio una zapatilla", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(5, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles facil", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(6, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Arreglo de fusibles", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(7, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Instalacion de tomacorrientes", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(8, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio una tuberia en la cocina", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(9, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompieron las tuberias del baño", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(10, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio la caldera", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(11, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio la caldera denuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null),
            new JobContract(12, CLIENT, JOB_PACKAGES[0], PROFESSIONAL, LocalDateTime.now(), "Se me rompio la caldera denuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE), null)
    };


    private static final int JOB_CONTRACTS_PRO1_COUNT = 18;

    private static final int JOB_CONTRACTS_COUNT = 18;

    private static final int JOB_CONTRACTS_POST_COUNT = 12;


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
        Mockito.when(mockJobPackageDao.findById(JOB_PACKAGES[0].getId()))
                .thenReturn(Optional.of(JOB_PACKAGES[0]));

        JobContract jobContract = jobContractDaoJDBC.create(CLIENT.getId(), JOB_PACKAGES[0].getId(), JOB_CONTRACTS_PACKAGE1[0].getDescription(),
                new ByteImage(IMAGE_DATA, IMAGE_TYPE));

        Assert.assertNotNull(jobContract);

        Assert.assertEquals(CLIENT, jobContract.getClient());
        Assert.assertEquals(CLIENT.getUsername(), jobContract.getClient().getUsername());
        Assert.assertEquals(CLIENT.getPhone(), jobContract.getClient().getPhone());
        Assert.assertEquals(CLIENT.isActive(), jobContract.getClient().isActive());

        Assert.assertEquals(PROFESSIONAL, jobContract.getProfessional());
        Assert.assertEquals(PROFESSIONAL.getEmail(), jobContract.getProfessional().getEmail());
        Assert.assertEquals(PROFESSIONAL.getUsername(), jobContract.getProfessional().getUsername());
        Assert.assertEquals(PROFESSIONAL.getPhone(), jobContract.getProfessional().getPhone());
        Assert.assertEquals(PROFESSIONAL.isActive(), jobContract.getProfessional().isActive());

        Assert.assertEquals(JOB_PACKAGES[0], jobContract.getJobPackage());
        Assert.assertEquals(JOB_PACKAGES[0].getPostId(), jobContract.getJobPackage().getPostId());
        Assert.assertEquals(JOB_PACKAGES[0].getDescription(), jobContract.getJobPackage().getDescription());
        Assert.assertEquals(JOB_PACKAGES[0].getTitle(), jobContract.getJobPackage().getTitle());
        Assert.assertEquals(JOB_PACKAGES[0].getPrice(), jobContract.getJobPackage().getPrice(), 0.001);
        Assert.assertEquals(JOB_PACKAGES[0].getRateType(), jobContract.getJobPackage().getRateType());
        Assert.assertEquals(JOB_PACKAGES[0].is_active(), jobContract.getJobPackage().is_active());

        Assert.assertNotNull(jobContract.getCreationDate());
        Assert.assertEquals(JOB_CONTRACTS_COUNT + 1, jobContract.getId());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getDescription(), jobContract.getDescription());
    }

    @Test
    public void testFindById() {
        Optional<JobContract> jobContract = jobContractDaoJDBC.findById(JOB_CONTRACTS_PACKAGE1[0].getId());
        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getId(), jobContract.get().getId());
    }

    @Test
    public void testFindByClientIdWithoutPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByClientId(CLIENT.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByClientId(CLIENT.getId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(HirenetUtils.PAGE_SIZE, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByProIdWithoutPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByProId(PROFESSIONAL.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByProId(PROFESSIONAL.getId(), 0);

        Assert.assertEquals(HirenetUtils.PAGE_SIZE, jobContracts.size());
        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByPackageIdWithoutPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByPackageId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length, jobContracts.size());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getId(), jobContracts.get(0).getJobPackage().getId());
    }

    @Test
    public void testFindByPackageIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByPackageId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(HirenetUtils.PAGE_SIZE, jobContracts.size());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getId(), jobContracts.get(0).getJobPackage().getId());
    }

    @Test
    public void testFindByPostIdWithoutPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_POST_COUNT, jobContracts.size()); //3 del package1 y 1 del package2
        jobContracts.forEach(jobContract ->
                Assert.assertEquals(JOB_POST.getId(), jobContract.getJobPackage().getPostId()));
    }

    @Test
    public void testFindByPostIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(HirenetUtils.PAGE_SIZE, jobContracts.size()); //3 del package1 y 1 del package2
        jobContracts.forEach(jobContract ->
                Assert.assertEquals(JOB_POST.getId(), jobContract.getJobPackage().getPostId()));
    }


    @Test
    public void testFindContractsQuantityByProId() {
        int ans = jobContractDaoJDBC.findContractsQuantityByProId(PROFESSIONAL.getId());

        Assert.assertEquals(JOB_CONTRACTS_COUNT, ans);
    }

    @Test
    public void testFindContractsQuantityByNotExistingProId() {
        int ans = jobContractDaoJDBC.findContractsQuantityByProId(PROFESSIONAL.getId() + 100);

        Assert.assertEquals(0, ans);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateWithNonExistentClient() {
        jobContractDaoJDBC.create(999, JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(), JOB_CONTRACTS_PACKAGE1[0].getDescription());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateWithNonExistentPackage() {
        jobContractDaoJDBC.create(CLIENT.getId(), 999, JOB_CONTRACTS_PACKAGE1[0].getDescription());
    }

    @Test
    public void testFindContractsQuantityByPostId() {
        int qty = jobContractDaoJDBC.findContractsQuantityByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length, qty);
    }

    @Test
    public void testFindCMaxPageContractsByClientId() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByClientId(CLIENT.getId());
        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PACKAGE1.length / HirenetUtils.PAGE_SIZE), qty);
    }

    @Test
    public void testFindCMaxPageContractsByClientIdWithNonExistentClient() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByClientId(999);
        Assert.assertEquals(0, qty);
    }

    @Test
    public void testFindCMaxPageContractsByProId() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByProId(PROFESSIONAL.getId());
        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PRO1_COUNT/ HirenetUtils.PAGE_SIZE), qty);
    }

    @Test
    public void testFindCMaxPageContractsByProIdWithNonExistentPro() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByProId(999);
        Assert.assertEquals(0, qty);
    }
}
