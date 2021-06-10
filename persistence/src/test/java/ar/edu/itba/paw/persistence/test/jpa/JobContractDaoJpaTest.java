package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.JobContractDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
import ar.edu.itba.paw.models.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job_contract_data_test.sql")
@Transactional
public class JobContractDaoJpaTest {
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
    private static final List<JobPost.Zone> ZONES =
            new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));
    private static final JobPost JOB_POST = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            true,
            LocalDateTime.now()
    );

    private static final JobPackage[] JOB_PACKAGES = {
            new JobPackage(
                    1,
                    JOB_POST,
                    "Trabajo Simple",
                    "Arreglo basico de electrodomesticos",
                    200.0,
                    JobPackage.RateType.values()[0],
                    true
            ), new JobPackage(
            2,
            JOB_POST,
            "Trabajo no tan simple",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            3,
            JOB_POST,
            "Trabajo simple 2",
            "Arreglo basico de electrodomesticos",
            200.0,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            4,
            JOB_POST,
            "Trabajo no tan simple 2",
            "Instalacion de cableado electrico",
            850.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            5,
            JOB_POST,
            "Trabajo Complejo 2",
            "Arreglos de canerias",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            6,
            JOB_POST,
            "Trabajo barato 2",
            "Arreglos varios",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            7,
            JOB_POST,
            "Trabajo barato 2",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            8,
            JOB_POST,
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    ), new JobPackage(
            9,
            JOB_POST,
            "Trabajo Experto 2",
            "Presupuesto y desarrollo de proyectos",
            500.00,
            JobPackage.RateType.values()[0],
            true
    )
    };

    private static final byte[] IMAGE_DATA = "image_data_for_testing".getBytes(StandardCharsets.UTF_8);

    private static final String IMAGE_TYPE = "image/jpg";

    private static final ByteImage CONTRACT1_IMAGE = new ByteImage(new byte[]{1, 2, 3, 4, 5, 6},"png");

    private static final List<JobContract.ContractState> ALL_STATES = Arrays.asList(JobContract.ContractState.values());

    //Se inicializan con state en pending
    private static final JobContractWithImage[] JOB_CONTRACTS_PACKAGE1 = new JobContractWithImage[]{
            new JobContractWithImage(1, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now(), "Se me rompio una zapatilla",new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(2, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(1),"Arreglo de fusibles facil", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(3, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(2),"Arreglo de fusibles", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(4, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(3),"Se me rompio una zapatilla", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(5, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(4),"Arreglo de fusibles facil", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(6, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(5),"Arreglo de fusibles", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(8, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(7),"Se me rompio una tuberia en la cocina", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(9, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(8),"Se me rompieron las tuberias del baño", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(10, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(9),"Se me rompio la caldera", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(11, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(10),"Se me rompio la caldera denuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(12, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(11),"Se me rompio la caldera denuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(19,CLIENT,JOB_PACKAGES[0],LocalDateTime.now(),LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(12),"Se me rompio la caldera denuevo",new ByteImage(IMAGE_DATA,IMAGE_TYPE) ),
            new JobContractWithImage(7, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(6),"Instalacion de tomacorrientes", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(13, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(13),"Se me rompio una tuberia en la cocina", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(14, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(14),"Se me rompieron las tuberias del banio", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(15, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(15),"Se me rompio la caldera", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(16, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(16),"Se me rompio la caldera de nuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(17, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(17),"Se me rompio la caldera de nuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),
            new JobContractWithImage(18, CLIENT, JOB_PACKAGES[0], LocalDateTime.now(), LocalDateTime.now().plusDays(5),LocalDateTime.now().minusDays(18),"Se me rompio la caldera de nuevo", new ByteImage(IMAGE_DATA, IMAGE_TYPE)),


    };

    private static final int JOB_CONTRACTS_PRO1_COUNT = 19;

    private static final int JOB_CONTRACTS_PRO1_COUNT_PENDING = 18;
    private static final int JOB_CONTRACTS_PRO1_COUNT_APPROVED = 1;


    private static final int JOB_CONTRACTS_COUNT = 19;

    private static final int JOB_CONTRACTS_POST_COUNT = 13;

    private static final int JOB_CARDS_PAGE_1 = 8;

    private static final int NON_EXISTENT_ID = 999;

    @PersistenceContext
    private EntityManager em;


    @Autowired
    private DataSource ds;

    @InjectMocks
    @Autowired
    private JobContractDaoJpa jobContractDaoJpa;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {

        JobContractWithImage jobContract = jobContractDaoJpa.create(CLIENT.getId(), JOB_PACKAGES[0].getId(),
                JOB_CONTRACTS_PACKAGE1[0].getDescription(), LocalDateTime.now().plusDays(5),new ByteImage(IMAGE_DATA, IMAGE_TYPE));
        em.flush();
        Assert.assertNotNull(jobContract);
        Assert.assertEquals(CLIENT, jobContract.getClient());
        Assert.assertEquals(CLIENT.getUsername(), jobContract.getClient().getUsername());
        Assert.assertEquals(CLIENT.getPhone(), jobContract.getClient().getPhone());
        Assert.assertEquals(CLIENT.isActive(), jobContract.getClient().isActive());

        Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId());
        Assert.assertEquals(PROFESSIONAL.getEmail(), jobContract.getProfessional().getEmail());
        Assert.assertEquals(PROFESSIONAL.getUsername(), jobContract.getProfessional().getUsername());
        Assert.assertEquals(PROFESSIONAL.getPhone(), jobContract.getProfessional().getPhone());
        Assert.assertEquals(PROFESSIONAL.isActive(), jobContract.getProfessional().isActive());

        Assert.assertEquals(JOB_PACKAGES[0], jobContract.getJobPackage());
        Assert.assertEquals(JOB_PACKAGES[0].getJobPost().getId(), jobContract.getJobPackage().getJobPost().getId());

        Assert.assertEquals(JOB_PACKAGES[0].getDescription(), jobContract.getJobPackage().getDescription());
        Assert.assertEquals(JOB_PACKAGES[0].getTitle(), jobContract.getJobPackage().getTitle());
        Assert.assertEquals(JOB_PACKAGES[0].getPrice(), jobContract.getJobPackage().getPrice(), 0.001);
        Assert.assertEquals(JOB_PACKAGES[0].getRateType(), jobContract.getJobPackage().getRateType());
        Assert.assertEquals(JOB_PACKAGES[0].is_active(), jobContract.getJobPackage().is_active());

        Assert.assertNotNull(jobContract.getCreationDate());
        Assert.assertEquals(JOB_CONTRACTS_COUNT + 1, jobContract.getId());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getDescription(), jobContract.getDescription());

        Assert.assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate,"contract"),20);
    }


    @Test(expected = PersistenceException.class )
    public void testCreateWithInvalidDescription(){
        jobContractDaoJpa.create(CLIENT.getId(),JOB_PACKAGES[0].getId(),null,LocalDateTime.now());
    }

    @Test(expected = UserNotFoundException.class)
    public void testCreateWithNonExistentClient() {

        jobContractDaoJpa.create(NON_EXISTENT_ID, JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(), JOB_CONTRACTS_PACKAGE1[0].getDescription(),LocalDateTime.now());

    }

    @Test(expected = JobPackageNotFoundException.class)
    public void testCreateWithNonExistentPackage() {

        jobContractDaoJpa.create(CLIENT.getId(), NON_EXISTENT_ID, JOB_CONTRACTS_PACKAGE1[0].getDescription(),LocalDateTime.now());

    }

    @Test
    public void testFindById() {

        Optional<JobContract> jobContract = jobContractDaoJpa.findById(JOB_CONTRACTS_PACKAGE1[0].getId());

        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getId(), jobContract.get().getId());
    }

    @Test
    public void testFindByIdNotExists() {

        Optional<JobContract> jobContract = jobContractDaoJpa.findById(NON_EXISTENT_ID);

        Assert.assertFalse(jobContract.isPresent());
    }

    @Test
    public void testFindByClientIdWherePendingAndWithoutPagination() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id = " + CLIENT.getId() + " AND contract_state = " + JobContract.ContractState.PENDING_APPROVAL.ordinal());

        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(), Collections.singletonList(JobContract.ContractState.PENDING_APPROVAL), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByProClientWhereApprovedAndWithoutPagination() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id = " + CLIENT.getId() + " AND contract_state = " + JobContract.ContractState.APPROVED.ordinal());

        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(), Collections.singletonList(JobContract.ContractState.APPROVED), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWherePendingAndWithPagination() {
        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id = " + CLIENT.getId() + " AND contract_state = " + JobContract.ContractState.PENDING_APPROVAL.ordinal());
        realQty = Math.min(realQty,HirenetUtils.PAGE_SIZE);
        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(), Collections.singletonList(JobContract.ContractState.PENDING_APPROVAL), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWhereApprovedAndWithPagination() {
        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id = " + CLIENT.getId() + " AND contract_state = " + JobContract.ContractState.APPROVED.ordinal());
        realQty = Math.min(realQty,HirenetUtils.PAGE_SIZE);
        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(), Collections.singletonList(JobContract.ContractState.APPROVED), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWhereApprovedAndPendingAndWithoutPagination() {
        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id = " + CLIENT.getId() + " AND (contract_state = " + JobContract.ContractState.APPROVED.ordinal() + " OR contract_state = " + JobContract.ContractState.PENDING_APPROVAL.ordinal() + ")");
        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(), Arrays.asList(JobContract.ContractState.APPROVED, JobContract.ContractState.PENDING_APPROVAL), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWhereApprovedAndPendingAndWithPagination() {
        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id = " + CLIENT.getId() + " AND (contract_state = " + JobContract.ContractState.APPROVED.ordinal() + " OR contract_state = " + JobContract.ContractState.PENDING_APPROVAL.ordinal() + ")");
        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(), Arrays.asList(JobContract.ContractState.APPROVED, JobContract.ContractState.PENDING_APPROVAL), 0);
        realQty = Math.min(realQty, HirenetUtils.PAGE_SIZE);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWithoutPagination() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id  = " + CLIENT.getId());

        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(),ALL_STATES, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }

    @Test
    public void testFindByClientIdWithPagination() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id  = " + CLIENT.getId());
        realQty = Math.min(realQty, HirenetUtils.PAGE_SIZE);

        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(CLIENT.getId(),ALL_STATES, 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(CLIENT.getId(), jobContract.getClient().getId()));
    }


    @Test
    public void testFindByClientIdWithInvalidId() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByClientId(NON_EXISTENT_ID,ALL_STATES, 0);

        Assert.assertTrue(jobContracts.isEmpty());
    }

    @Test
    public void testFindByProIdWherePendingAndWithoutPagination() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(), Collections.singletonList(JobContract.ContractState.PENDING_APPROVAL), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PRO1_COUNT_PENDING, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWhereApprovedAndWithoutPagination() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(), Collections.singletonList(JobContract.ContractState.APPROVED), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PRO1_COUNT_APPROVED, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWherePendingAndWithPagination() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(), Collections.singletonList(JobContract.ContractState.PENDING_APPROVAL), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CARDS_PAGE_1, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWhereApprovedAndWithPagination() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(), Collections.singletonList(JobContract.ContractState.APPROVED), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PRO1_COUNT_APPROVED, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWhereApprovedAndPendingAndWithoutPagination() {
        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(), Arrays.asList(JobContract.ContractState.APPROVED, JobContract.ContractState.PENDING_APPROVAL), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PRO1_COUNT, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWhereApprovedAndPendingAndWithPagination() {
        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(), Arrays.asList(JobContract.ContractState.APPROVED, JobContract.ContractState.PENDING_APPROVAL), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CARDS_PAGE_1, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWithoutPagination() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(),ALL_STATES, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PRO1_COUNT, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWithPagination() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(PROFESSIONAL.getId(),ALL_STATES, 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CARDS_PAGE_1, jobContracts.size());
        jobContracts.forEach(jobContract -> Assert.assertEquals(PROFESSIONAL.getId(), jobContract.getProfessional().getId()));
    }

    @Test
    public void testFindByProIdWithInvalidId() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByProId(NON_EXISTENT_ID,ALL_STATES, 0);

        Assert.assertTrue(jobContracts.isEmpty());
    }

    @Test
    public void testFindByPackageIdWithoutPagination() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","package_id = "+ JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId());

        List<JobContract> jobContracts = jobContractDaoJpa.findByPackageId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getId(), jobContracts.get(0).getJobPackage().getId());
    }

    @Test
    public void testFindByPackageIdWithPagination() {
        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","client_id  = " + CLIENT.getId());
        realQty = Math.min(realQty, HirenetUtils.PAGE_SIZE);

        List<JobContract> jobContracts = jobContractDaoJpa.findByPackageId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1[0].getId(), jobContracts.get(0).getJobPackage().getId());
    }

    @Test
    public void testFindByPackageIdWithInvalidId() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByPackageId(NON_EXISTENT_ID, 0);

        Assert.assertTrue(jobContracts.isEmpty());

    }

    @Test
    public void testFindByPostIdWithoutPagination() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","package_id = " + JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId());

        List<JobContract> jobContracts = jobContractDaoJpa.findByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getJobPost().getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size());
        jobContracts.forEach(jobContract ->
                Assert.assertEquals(JOB_POST.getId(), jobContract.getJobPackage().getPostId()));
    }

    @Test
    public void testFindByPostIdWithPagination() {
        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","package_id = " + JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getId());
        realQty = Math.min(realQty, HirenetUtils.PAGE_SIZE);

        List<JobContract> jobContracts = jobContractDaoJpa.findByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId(), 0);

        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(realQty, jobContracts.size()); //3 del package1 y 1 del package2
        jobContracts.forEach(jobContract ->
                Assert.assertEquals(JOB_POST.getId(), jobContract.getJobPackage().getPostId()));
    }

    @Test
    public void testFindByPostIdWithInvalidId() {

        List<JobContract> jobContracts = jobContractDaoJpa.findByPostId(NON_EXISTENT_ID, 0);

        Assert.assertTrue(jobContracts.isEmpty());
    }


    @Test
    public void testFindContractsQuantityByProId() {

        int ans = jobContractDaoJpa.findContractsQuantityByProId(PROFESSIONAL.getId());

        Assert.assertEquals(JOB_CONTRACTS_COUNT, ans);
    }

    @Test
    public void testFindContractsQuantityByNotExistingProId() {

        int ans = jobContractDaoJpa.findContractsQuantityByProId(PROFESSIONAL.getId() + 100);

        Assert.assertEquals(0, ans);
    }

    @Test
    public void testFindContractsQuantityByPostId() {

        int realQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","package_id = " + JOB_PACKAGES[0].getId());

        int qty = jobContractDaoJpa.findContractsQuantityByPostId(JOB_CONTRACTS_PACKAGE1[0].getJobPackage().getPostId());

        Assert.assertEquals(realQty, qty);
    }

    @Test
    public void testFindCMaxPageContractsByClientIdWithPendingState() {

        int rowQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","contract_state = 0 AND client_id = " + CLIENT.getId());

        int qty = jobContractDaoJpa.findMaxPageContractsByClientId(CLIENT.getId(), Collections.singletonList(JobContract.ContractState.PENDING_APPROVAL));

        Assert.assertEquals((int) Math.ceil((double) rowQty / HirenetUtils.PAGE_SIZE), qty);
    }

    @Test
    public void testFindCMaxPageContractsByClientIdWithApprovedState() {

        int rowQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","contract_state = 1 AND client_id = " + CLIENT.getId());

        int qty = jobContractDaoJpa.findMaxPageContractsByClientId(CLIENT.getId(), Collections.singletonList(JobContract.ContractState.APPROVED));


        Assert.assertEquals((int) Math.ceil((double) rowQty / HirenetUtils.PAGE_SIZE), qty);
    }



    @Test
    public void testFindCMaxPageContractsByClientIdWithNonExistentClient() {

        int qty = jobContractDaoJpa.findMaxPageContractsByClientId(NON_EXISTENT_ID, Collections.singletonList(JobContract.ContractState.APPROVED));

        Assert.assertEquals(0, qty);
    }

    @Test
    public void testFindCMaxPageContractsByProId() {

        int qty = jobContractDaoJpa.findMaxPageContractsByProId(PROFESSIONAL.getId(),ALL_STATES);

        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PRO1_COUNT/ HirenetUtils.PAGE_SIZE), qty);
    }

    @Test
    public void testFindCMaxPageContractsByProIdWithNonExistentPro() {

        int qty = jobContractDaoJpa.findMaxPageContractsByProId(NON_EXISTENT_ID,Collections.singletonList(JobContract.ContractState.APPROVED));

        Assert.assertEquals(0, qty);
    }

    @Test
    public void testFindCMaxPageContractsByProIdWithPendingState() {


        int qty = jobContractDaoJpa.findMaxPageContractsByProId(PROFESSIONAL.getId(), Collections.singletonList(JobContract.ContractState.PENDING_APPROVAL));

        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PRO1_COUNT_PENDING / HirenetUtils.PAGE_SIZE), qty);
    }

    @Test
    public void testFindCMaxPageContractsByProIdWithApprovedState() {


        int qty = jobContractDaoJpa.findMaxPageContractsByProId(PROFESSIONAL.getId(), Collections.singletonList(JobContract.ContractState.APPROVED));


        Assert.assertEquals((int) Math.ceil((double) JOB_CONTRACTS_PRO1_COUNT_APPROVED / HirenetUtils.PAGE_SIZE), qty);
    }

    @Test
    public void testChangeContractState(){
        JobContract.ContractState state = JobContract.ContractState.PRO_REJECTED;
        jobContractDaoJpa.changeContractState(JOB_CONTRACTS_PACKAGE1[0].getId(),state);
        em.flush();
        JobContract dbContract = em.find(JobContract.class,JOB_CONTRACTS_PACKAGE1[0].getId());

        int rejectedQty = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"contract","contract_state = " +state.ordinal() );
        Assert.assertEquals(rejectedQty,1);
        Assert.assertEquals(dbContract,new JobContract(JOB_CONTRACTS_PACKAGE1[0]));
        Assert.assertEquals(dbContract.getState(), state);
    }

    @Test
    public void testFindJobContractWithImage(){
        Optional<JobContractWithImage> jobContract = jobContractDaoJpa.findJobContractWithImage(JOB_CONTRACTS_PACKAGE1[0].getId());
        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(CONTRACT1_IMAGE,jobContract.get().getByteImage());

    }

    @Test
    public void testFindJobContractWithImageWithNonExistentId(){
        Optional<JobContractWithImage> jobContract = jobContractDaoJpa.findJobContractWithImage(NON_EXISTENT_ID);
        Assert.assertFalse(jobContract.isPresent());
    }

    @Test
    public void testFindImageByContractId(){
        Optional<ByteImage> jobContract = jobContractDaoJpa.findImageByContractId(JOB_CONTRACTS_PACKAGE1[0].getId());
        Assert.assertTrue(jobContract.isPresent());
        Assert.assertEquals(CONTRACT1_IMAGE,jobContract.get());
    }

    @Test
    public void testFindImageByContractIdWithNonExistentId(){
        Optional<ByteImage> jobContract = jobContractDaoJpa.findImageByContractId(NON_EXISTENT_ID);
        Assert.assertFalse(jobContract.isPresent());
    }

    @Test
    public void testFindClientByContractId() {
        Optional<User> client = jobContractDaoJpa.findClientByContractId(JOB_CONTRACTS_PACKAGE1[0].getId());
        Assert.assertTrue(client.isPresent());
        Assert.assertEquals(CLIENT,client.get());
    }

    @Test
    public void testFindClientByContractIdWithNonExistentId() {
        Optional<User> client = jobContractDaoJpa.findClientByContractId(NON_EXISTENT_ID);
        Assert.assertFalse(client.isPresent());
    }

    @Test
    public void testFindByClientIdAndSortedByModificationDate() {
        List<JobContract> jobContracts = jobContractDaoJpa.findByClientIdAndSortedByModificationDate(CLIENT.getId(),ALL_STATES,HirenetUtils.ALL_PAGES);
        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length,jobContracts.size());

        List<JobContract> sortedContracts = Arrays.stream(JOB_CONTRACTS_PACKAGE1).sorted((o1, o2) -> o2.getLastModifiedDate().compareTo(o1.getLastModifiedDate())).map(JobContract::new).collect(Collectors.toList());
        Assert.assertEquals(sortedContracts,jobContracts);
    }

    @Test
    public void testFindByClientIdAndSortedByModificationDateComparingToNonSortedFails() {
        List<JobContract> jobContracts = jobContractDaoJpa.findByClientIdAndSortedByModificationDate(CLIENT.getId(),ALL_STATES,HirenetUtils.ALL_PAGES);
        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length,jobContracts.size());

        Assert.assertNotEquals(Arrays.stream(JOB_CONTRACTS_PACKAGE1).map(JobContract::new).collect(Collectors.toList()), jobContracts);
    }

    @Test
    public void testFindByProIdAndSortedByModificationDate() {
        List<JobContract> jobContracts = jobContractDaoJpa.findByProIdAndSortedByModificationDate(PROFESSIONAL.getId(),ALL_STATES,HirenetUtils.ALL_PAGES);
        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length,jobContracts.size());

        List<JobContract> sortedContracts = Arrays.stream(JOB_CONTRACTS_PACKAGE1).sorted((o1, o2) -> o2.getLastModifiedDate().compareTo(o1.getLastModifiedDate())).map(JobContract::new).collect(Collectors.toList());
        Assert.assertEquals(sortedContracts,jobContracts);
    }

    @Test
    public void testFindByProIdAndSortedByModificationDateComparingToNonSortedFails() {
        List<JobContract> jobContracts = jobContractDaoJpa.findByProIdAndSortedByModificationDate(PROFESSIONAL.getId(), ALL_STATES, HirenetUtils.ALL_PAGES);
        Assert.assertFalse(jobContracts.isEmpty());
        Assert.assertEquals(JOB_CONTRACTS_PACKAGE1.length, jobContracts.size());

        Assert.assertNotEquals(Arrays.stream(JOB_CONTRACTS_PACKAGE1).map(JobContract::new).collect(Collectors.toList()), jobContracts);
    }
}
