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
@Sql("classpath:db_data_test.sql")
public class JobContractDaoJDBCTest {
    private static final List<UserAuth.Role> USER1_ROLES = Arrays.asList(UserAuth.Role.CLIENT, UserAuth.Role.PROFESSIONAL);
    private static final List<UserAuth.Role> USER2_ROLES = Arrays.asList(UserAuth.Role.CLIENT);

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
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    4,
                    USER1,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    5,
                    USER1,
                    "Electricista Matriculado 2",
                    "Lun a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    6,
                    USER1,
                    "Paseador de perros 2",
                    "Viernes a sabados 09hs - 14hs",
                    JobPost.JobType.values()[3],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    7,
                    USER1,
                    "Electricista no matriculado 2",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    8,
                    USER1,
                    "Plomero Matriculado 2",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    9,
                    USER1,
                    "Plomero Matriculado 3",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    10,
                    USER1,
                    "Plomero Matriculado 4",
                    "Miercoles a Viernes 10hs - 14hs",
                    JobPost.JobType.values()[2],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            ),
            new JobPost(
                    3,
                    USER2,
                    "Electricista no matriculado",
                    "Lun a Jueves 13hs - 14hs",
                    JobPost.JobType.values()[1],
                    ZONES, 0.0,
                    true,
                    LocalDateTime.now()
            )};

    private static final JobPost INACTIVE_JOB_POST = new JobPost(
            11,
            USER1,
            "Plomero Inactivo",
            "Miercoles a Viernes 10hs - 14hs",
            JobPost.JobType.values()[2],
            ZONES, 0.0,
            true,
            LocalDateTime.now()

    );

    private static final JobPackage[] JOB_PACKAGES = {
            new JobPackage(
                    1,
                    JOB_POSTS[0].getId(),
                    "Trabajo simple",
                    "Arreglo basico de electrodomesticos",
                    200.0,
                    JobPackage.RateType.values()[0],
                    true
            ), new JobPackage(
            2,
            JOB_POSTS[0].getId(),
            "Trabajo no tan simple",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            6,
            JOB_POSTS[0].getId(),
            "Trabajo simple 2",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            7,
            JOB_POSTS[0].getId(),
            "Trabajo no tan simple 2",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            8,
            JOB_POSTS[0].getId(),
            "Trabajo Complejo 2",
            "Arreglos de canerias",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            9,
            JOB_POSTS[0].getId(),
            "Trabajo barato 2",
            "Arreglos varios",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            10,
            JOB_POSTS[0].getId(),
            "Trabajo barato 2",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            11,
            JOB_POSTS[0].getId(),
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            12,
            JOB_POSTS[0].getId(),
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
            new JobContract(1,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio una zapatilla",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(2,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles facil",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(3,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(10,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio una zapatilla",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(11,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles facil",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(12,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Arreglo de fusibles",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(13,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Instalacion de tomacorrientes",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(14,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio una tuberia en la cocina",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(15,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompieron las tuberias del baño",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(16,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio la caldera",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(17,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio la caldera denuevo",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContract(18,USER2,JOB_PACKAGES[0],USER1,LocalDateTime.now(),"Se me rompio la caldera denuevo",new ByteImage(IMAGE_DATA, IMAGE_TYPE))
    };



    private static final int JOB_CONTRACTS_PRO1_QUANTITY = 12;


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

        Mockito.when(mockPostDao.findById(Mockito.eq(JOB_POSTS[0].getId())))
                .thenReturn(Optional.of(JOB_POSTS[0]));
        Mockito.when(mockUserDao.findById(Mockito.eq(USER2.getId())))
                .thenReturn(Optional.of(USER2));
        Mockito.when(mockUserDao.findById(Mockito.eq(USER1.getId())))
                .thenReturn(Optional.of(USER1));
        Mockito.when(mockJobPackageDao.findById(JOB_PACKAGES[0].getId()))
                .thenReturn(Optional.of(JOB_PACKAGES[0]));

        JobContract jobContract = jobContractDaoJDBC.create(USER2.getId(), JOB_PACKAGES[0].getId(), JOB_CONTRACTS_PACKAGE1[0].getDescription(),
                new ByteImage(IMAGE_DATA, IMAGE_TYPE));

        Assert.assertNotNull(jobContract);

        Assert.assertEquals(USER2, jobContract.getClient());
        Assert.assertEquals(USER2.getUsername(), jobContract.getClient().getUsername());
        Assert.assertEquals(USER2.getPhone(), jobContract.getClient().getPhone());
        Assert.assertEquals(USER2.isActive(), jobContract.getClient().isActive());

        Assert.assertEquals(USER1, jobContract.getProfessional());
        Assert.assertEquals(USER1.getEmail(), jobContract.getProfessional().getEmail());
        Assert.assertEquals(USER1.getUsername(), jobContract.getProfessional().getUsername());
        Assert.assertEquals(USER1.getPhone(), jobContract.getProfessional().getPhone());
        Assert.assertEquals(USER1.isActive(), jobContract.getProfessional().isActive());

        Assert.assertEquals(JOB_PACKAGES[0], jobContract.getJobPackage());
        Assert.assertEquals(JOB_PACKAGES[0].getPostId(), jobContract.getJobPackage().getPostId());
        Assert.assertEquals(JOB_PACKAGES[0].getDescription(), jobContract.getJobPackage().getDescription());
        Assert.assertEquals(JOB_PACKAGES[0].getTitle(), jobContract.getJobPackage().getTitle());
        Assert.assertEquals(JOB_PACKAGES[0].getPrice(), jobContract.getJobPackage().getPrice(), 0.001);
        Assert.assertEquals(JOB_PACKAGES[0].getRateType(), jobContract.getJobPackage().getRateType());
        Assert.assertEquals(JOB_PACKAGES[0].is_active(), jobContract.getJobPackage().is_active());

        Assert.assertNotNull(jobContract.getCreationDate());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[JOB_CONTRACTS_PACKAGE1.length-1].getId() + 1, jobContract.getId());
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
        List<JobContract> jobContracts = jobContractDaoJDBC.findByClientId(USER2.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(USER2.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByClientId(USER2.getId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(HirenetUtils.PAGE_SIZE,jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(USER2.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByProIdWithoutPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByProId(USER1.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(USER1.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByProId(USER1.getId(), 0);

        Assert.assertEquals(HirenetUtils.PAGE_SIZE,jobContracts.size());
        Assert.assertFalse(jobContracts.isEmpty());
        jobContracts.forEach(jobContract -> Assert.assertEquals(USER1.getId(), jobContract.getProfessional().getId()));
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
        Assert.assertEquals(JOB_CONTRACTS_PRO1_QUANTITY, jobContracts.size()); //3 del package1 y 1 del package2
        jobContracts.forEach(jobContract ->
                Assert.assertEquals(JOB_POSTS[0].getId(), jobContract.getJobPackage().getPostId()));
    }

    @Test
    public void testFindByPostIdWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJDBC.findByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(HirenetUtils.PAGE_SIZE, jobContracts.size()); //3 del package1 y 1 del package2
        jobContracts.forEach(jobContract ->
                Assert.assertEquals(JOB_POSTS[0].getId(), jobContract.getJobPackage().getPostId()));
    }


    @Test
    public void testFindContractsQuantityByProId() {
        int ans = jobContractDaoJDBC.findContractsQuantityByProId(USER1.getId());

        Assert.assertEquals(JOB_CONTRACTS_PRO1_QUANTITY, ans);
    }

    @Test
    public void testFindContractsQuantityByNotExistingProId() {
        int ans = jobContractDaoJDBC.findContractsQuantityByProId(USER1.getId() + 100);

        Assert.assertEquals(0, ans);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateWithNonExistentClient(){
        jobContractDaoJDBC.create(999,JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(),JOB_CONTRACTS_PACKAGE1[0].getDescription());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateWithNonExistentPackage(){
        jobContractDaoJDBC.create(USER2.getId(),999,JOB_CONTRACTS_PACKAGE1[0].getDescription());
    }

    @Test
    public void testFindContractsQuantityByPostId() {
        int qty = jobContractDaoJDBC.findContractsQuantityByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length,qty);
    }

    @Test
    public void testFindCMaxPageContractsByClientId() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByClientId(JOB_CONTRACTS_PACKAGE1[0].getClient().getId());
        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PACKAGE1.length / HirenetUtils.PAGE_SIZE),qty);
    }

    @Test
    public void testFindCMaxPageContractsByClientIdWithNonExistentClient() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByClientId(999);
        Assert.assertEquals(0,qty);
    }

    @Test
    public void testFindCMaxPageContractsByProId() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByProId(JOB_CONTRACTS_PACKAGE1[0].getProfessional().getId());
        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PACKAGE1.length / HirenetUtils.PAGE_SIZE),qty);
    }

    @Test
    public void testFindCMaxPageContractsByProIdWithNonExistentPro() {
        int qty = jobContractDaoJDBC.findMaxPageContractsByProId(999);
        Assert.assertEquals(0,qty);
    }
}
