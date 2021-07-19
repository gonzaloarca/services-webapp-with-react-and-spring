package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.simple.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobContractServiceTest {

    private static final List<JobPost.Zone> ZONES =
            new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));
    private static final User CLIENT = new User(
            3, "manurodriguez@gmail.com", "Manuel Rodriguez", "0303456", true, true,
            LocalDateTime.now());
    private static final User PROFESSIONAL = new User(
            8, "franquesada@gmail.com", "Francisco Quesada", "0800111333", true, true,
            LocalDateTime.now());
    private static final JobPost JOB_POST1 = new JobPost(
            1,
            PROFESSIONAL,
            "Electricista Matriculado",
            "Lun a Viernes 10hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES,
            true,
            LocalDateTime.now()
    );
    private static final JobPost JOB_POST2 = new JobPost(
            2,
            PROFESSIONAL,
            "Paseador de perros",
            "Viernes a sabados 09hs - 14hs",
            JobPost.JobType.values()[3],
            ZONES,
            true,
            LocalDateTime.now()
    );
    private static final JobPackage JOB_PACKAGE = new JobPackage(
            7, JOB_POST2, "Arreglo avanzado de plomeria", "Todo tipo de canerias", 200.00, JobPackage.RateType.ONE_TIME, true
    );
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private static final String CONTRACT_DESCRIPTION = "Problema en tuberias en la cocina";
    private static final byte[] image1Bytes = {1, 2, 3, 4, 5};
    private static final String image1Type = "image/png";
    private static final ByteImage BYTE_IMAGE = new ByteImage(image1Bytes, image1Type);

    private static final JobContractWithImage[] JOB_CONTRACTS = new JobContractWithImage[]{
            new JobContractWithImage(1, CLIENT, JOB_PACKAGE, LocalDateTime.now(), LocalDateTime.now().plusDays(5), LocalDateTime.now(), "Description",null),
            new JobContractWithImage(2, CLIENT, JOB_PACKAGE, LocalDateTime.now(), LocalDateTime.now().plusDays(10), LocalDateTime.now(), "Another description",null)
    };

    private static final JobCard JOB_CARD = new JobCard(JOB_POST2, JobPackage.RateType.ONE_TIME, 500.0, 0, 0, 0.0, null);

    private static final JobContractCard[] JOB_CONTRACT_CARDS = new JobContractCard[]{
            new JobContractCard(JOB_CONTRACTS[0], JOB_CARD, null, null),
            new JobContractCard(JOB_CONTRACTS[1], JOB_CARD, null, null)
    };
    private static final long NON_EXISTENT_ID = 99999;

    @InjectMocks
    private final SimpleJobContractService simpleJobContractService = new SimpleJobContractService();

    @Mock
    private SimpleReviewService simpleReviewService;

    @Mock
    private SimpleJobCardService simpleJobCardService;

    @Mock
    private MailingService mailingService;

    @Mock
    private JobContractDao jobContractDao;

    @Mock
    private SimpleJobPackageService simpleJobPackageService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(5);
        String timeStr = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Mockito.when(jobContractDao.create(Mockito.eq(CLIENT.getId()), Mockito.eq(JOB_PACKAGE.getId()),
                Mockito.eq(JOB_PACKAGE.getDescription()), Mockito.eq(time)))
                .thenReturn(new JobContractWithImage(7, CLIENT, JOB_PACKAGE,
                        CREATION_DATE, time, CREATION_DATE, CONTRACT_DESCRIPTION, BYTE_IMAGE));
        Mockito.doNothing().when(mailingService).sendContractEmail(Mockito.any(), Mockito.any(), Mockito.any());

        Mockito.when(simpleJobPackageService.findByOnlyId(Mockito.eq(JOB_PACKAGE.getId()))).thenReturn(JOB_PACKAGE);

        JobContractWithImage maybeContract = simpleJobContractService.create(CLIENT.getId(), JOB_PACKAGE.getId(),
                JOB_PACKAGE.getDescription(), timeStr, Locale.getDefault(), "");

        Assert.assertNotNull(maybeContract);
        Assert.assertEquals(CREATION_DATE, maybeContract.getCreationDate());
        Assert.assertEquals(CONTRACT_DESCRIPTION, maybeContract.getDescription());
        Assert.assertEquals(JOB_PACKAGE, maybeContract.getJobPackage());
        Assert.assertEquals(CLIENT, maybeContract.getClient());
        Assert.assertEquals(PROFESSIONAL, maybeContract.getProfessional());
        Assert.assertEquals(BYTE_IMAGE, maybeContract.getByteImage());
    }

    @Test
    public void testFindJobContractCardsByProId() {
        List<JobContract.ContractState> states = new ArrayList<>();
        states.add(JobContract.ContractState.PENDING_APPROVAL);
        states.add(JobContract.ContractState.PRO_MODIFIED);
        states.add(JobContract.ContractState.CLIENT_MODIFIED);
        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);
        Mockito.doReturn(states).when(mockContractService).getContractStates(Mockito.eq("pending"));
        Mockito.doReturn(Arrays.asList(JOB_CONTRACTS)).when(mockContractService)
                .findByProIdAndSortedByModificationDateWithImage(
                        Mockito.eq(PROFESSIONAL.getId()), Mockito.eq(states), Mockito.eq(1));
        Mockito.when(simpleJobCardService.findByPostIdWithInactive(Mockito.eq(JOB_POST2.getId()))).thenReturn(JOB_CARD);
        Mockito.when(simpleReviewService.findContractReview(Mockito.anyLong())).thenReturn(Optional.empty());

        List<JobContractCard> jobContractCards = mockContractService
                .findContracts(PROFESSIONAL.getId(), "pending", "professional", 1);

        Assert.assertFalse(jobContractCards.isEmpty());
        Assert.assertEquals(Arrays.asList(JOB_CONTRACT_CARDS), jobContractCards);
    }

    @Test
    public void testFindJobContractCardsByProIdWithNonExistentId() {
        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);

        List<JobContractCard> jobContractCards = mockContractService.findContracts(NON_EXISTENT_ID,
                "pending", "professional", HirenetUtils.ALL_PAGES);

        Assert.assertTrue(jobContractCards.isEmpty());
    }

    @Test
    public void testFindJobContractCardsByClientId() {
        List<JobContract.ContractState> states = new ArrayList<>();
        states.add(JobContract.ContractState.PENDING_APPROVAL);
        states.add(JobContract.ContractState.PRO_MODIFIED);
        states.add(JobContract.ContractState.CLIENT_MODIFIED);
        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);
        Mockito.doReturn(states).when(mockContractService).getContractStates(Mockito.eq("pending"));
        Mockito.doReturn(Arrays.asList(JOB_CONTRACTS)).when(mockContractService)
                .findByClientIdAndSortedByModificationDateWithImage(
                        Mockito.eq(CLIENT.getId()), Mockito.eq(states), Mockito.eq(1));
        Mockito.when(simpleJobCardService.findByPostIdWithInactive(Mockito.eq(JOB_POST2.getId()))).thenReturn(JOB_CARD);
        Mockito.when(simpleReviewService.findContractReview(Mockito.anyLong())).thenReturn(Optional.empty());

        List<JobContractCard> jobContractCards = mockContractService
                .findContracts(CLIENT.getId(), "pending", "client", 1);

        Assert.assertFalse(jobContractCards.isEmpty());
        Assert.assertEquals(Arrays.asList(JOB_CONTRACT_CARDS), jobContractCards);
    }
}
