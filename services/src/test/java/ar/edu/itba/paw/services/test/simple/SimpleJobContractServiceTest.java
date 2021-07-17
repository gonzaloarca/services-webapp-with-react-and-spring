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
import java.time.temporal.ChronoUnit;
import java.util.*;

//@RunWith(MockitoJUnitRunner.class)
//public class SimpleJobContractServiceTest {
//
//    private static final List<JobPost.Zone> ZONES =
//            new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1],
//                    JobPost.Zone.values()[2]));
//    private static final User CLIENT = new User(
//            3, "manurodriguez@gmail.com", "Manuel Rodriguez", "0303456", true, true,
//            LocalDateTime.now());
//    private static final User PROFESSIONAL = new User(
//            8, "franquesada@gmail.com", "Francisco Quesada", "0800111333", true, true,
//            LocalDateTime.now());
//    private static final JobPost JOB_POST1 = new JobPost(
//            1,
//            PROFESSIONAL,
//            "Electricista Matriculado",
//            "Lun a Viernes 10hs - 14hs",
//            JobPost.JobType.values()[1],
//            ZONES,
//            true,
//            LocalDateTime.now()
//    );
//    private static final JobPost JOB_POST2 = new JobPost(
//            2,
//            PROFESSIONAL,
//            "Paseador de perros",
//            "Viernes a sabados 09hs - 14hs",
//            JobPost.JobType.values()[3],
//            ZONES,
//            true,
//            LocalDateTime.now()
//    );
//    private static final JobPackage JOB_PACKAGE = new JobPackage(
//            7, JOB_POST2, "Arreglo avanzado de plomeria", "Todo tipo de canerias", 200.00, JobPackage.RateType.ONE_TIME, true
//    );
//    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
//    private static final String CONTRACT_DESCRIPTION = "Problema en tuberias en la cocina";
//    private static final byte[] image1Bytes = {1, 2, 3, 4, 5};
//    private static final String image1Type = "image/png";
//    private static final ByteImage BYTE_IMAGE = new ByteImage(image1Bytes, image1Type);
//
//    private static final JobContract[] JOB_CONTRACTS = new JobContract[]{
//            new JobContract(1, CLIENT, JOB_PACKAGE, LocalDateTime.now(), LocalDateTime.now().plusDays(5), LocalDateTime.now(), "Description"),
//            new JobContract(2, CLIENT, JOB_PACKAGE, LocalDateTime.now(), LocalDateTime.now().plusDays(10), LocalDateTime.now(), "Another description")
//    };
//
//    private static final JobCard JOB_CARD = new JobCard(JOB_POST2, JobPackage.RateType.ONE_TIME, 500.0, 0, 0, 0.0, null);
//
//    private static final JobContractCard[] JOB_CONTRACT_CARDS = new JobContractCard[]{
//            new JobContractCard(JOB_CONTRACTS[0], JOB_CARD, null, null),
//            new JobContractCard(JOB_CONTRACTS[1], JOB_CARD, null, null)
//    };
//
//    @InjectMocks
//    private final SimpleJobContractService simpleJobContractService = new SimpleJobContractService();
//
//    @Mock
//    private SimpleUserService simpleUserService;
//
//    @Mock
//    private SimpleReviewService simpleReviewService;
//
//    @Mock
//    private SimpleJobCardService simpleJobCardService;
//
//    @Mock
//    private MailingService mailingService;
//
//    @Mock
//    private JobContractDao jobContractDao;
//
//    @Mock
//    MessageSource messageSource;
//
//    @Rule
//    public ExpectedException exceptionRule = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testCreate() {
//        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(5);
//
//        Mockito.when(jobContractDao.create(Mockito.eq(CLIENT.getId()), Mockito.eq(JOB_PACKAGE.getId()),
//                Mockito.eq(JOB_PACKAGE.getDescription()), Mockito.eq(time)))
//                .thenReturn(new JobContractWithImage(7, CLIENT, JOB_PACKAGE,
//                        CREATION_DATE, time, CREATION_DATE, CONTRACT_DESCRIPTION, BYTE_IMAGE));
//
//        JobContractWithImage maybeContract = simpleJobContractService.create(CLIENT.getId(), JOB_PACKAGE.getId(),
//                JOB_PACKAGE.getDescription(), time, Locale.getDefault(), "");
//
//        Assert.assertNotNull(maybeContract);
//        Assert.assertEquals(CREATION_DATE, maybeContract.getCreationDate());
//        Assert.assertEquals(CONTRACT_DESCRIPTION, maybeContract.getDescription());
//        Assert.assertEquals(JOB_PACKAGE, maybeContract.getJobPackage());
//        Assert.assertEquals(CLIENT, maybeContract.getClient());
//        Assert.assertEquals(PROFESSIONAL, maybeContract.getProfessional());
//        Assert.assertEquals(BYTE_IMAGE, maybeContract.getByteImage());
//    }
//
//    @Test
//    public void testFindJobContractCardsByProId() {
//        List<JobContract.ContractState> allStates = Arrays.asList(JobContract.ContractState.values());
//        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);
//        Mockito.doReturn(Arrays.asList(JOB_CONTRACTS)).when(mockContractService).findByProId(PROFESSIONAL.getId(), allStates, HirenetUtils.ALL_PAGES);
//        Mockito.when(simpleJobCardService.findByPostIdWithInactive(Mockito.eq(JOB_POST2.getId()))).thenReturn(JOB_CARD);
//        Mockito.when(simpleReviewService.findContractReview(Mockito.anyLong())).thenReturn(Optional.empty());
//        Locale.setDefault(new Locale("es", "ES"));
//        String datePattern = "dd/MM/yyyy H:mm";
//
//        Mockito.when(messageSource.getMessage(Mockito.eq("spring.mvc.format.date-time"),
//                Mockito.eq(null), Mockito.eq(Locale.getDefault()))).thenReturn(datePattern);
//        List<JobContractCard> jobContractCards = mockContractService.findJobContractCardsByProId(PROFESSIONAL.getId(), allStates, HirenetUtils.ALL_PAGES, Locale.getDefault());
//
//
//        Assert.assertFalse(jobContractCards.isEmpty());
//        Assert.assertEquals(Arrays.asList(JOB_CONTRACT_CARDS), jobContractCards);
//    }
//
//    @Test
//    public void testFindJobContractCardsByProIdWithNonExistentId() {
//        List<JobContract.ContractState> allStates = Arrays.asList(JobContract.ContractState.values());
//        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);
//
//
//        List<JobContractCard> jobContractCards = mockContractService.findJobContractCardsByProId(PROFESSIONAL.getId(), allStates, HirenetUtils.ALL_PAGES, Locale.getDefault());
//
//
//        Assert.assertTrue(jobContractCards.isEmpty());
//    }
//
//    @Test
//    public void testFindJobContractCardsByClientId() {
//        List<JobContract.ContractState> allStates = Arrays.asList(JobContract.ContractState.values());
//        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);
//        Mockito.doReturn(Arrays.asList(JOB_CONTRACTS)).when(mockContractService).findByProId(CLIENT.getId(), allStates, HirenetUtils.ALL_PAGES);
//        Mockito.when(simpleJobCardService.findByPostIdWithInactive(Mockito.eq(JOB_POST2.getId()))).thenReturn(JOB_CARD);
//        Mockito.when(simpleReviewService.findContractReview(Mockito.anyLong())).thenReturn(Optional.empty());
//        Locale.setDefault(new Locale("es", "ES"));
//        String datePattern = "dd/MM/yyyy H:mm";
//
//        Mockito.when(messageSource.getMessage(Mockito.eq("spring.mvc.format.date-time"),
//                Mockito.eq(null), Mockito.eq(Locale.getDefault()))).thenReturn(datePattern);
//        List<JobContractCard> jobContractCards = mockContractService.findJobContractCardsByProId(CLIENT.getId(), allStates, HirenetUtils.ALL_PAGES, Locale.getDefault());
//
//
//        Assert.assertFalse(jobContractCards.isEmpty());
//        Assert.assertEquals(Arrays.asList(JOB_CONTRACT_CARDS), jobContractCards);
//    }
//
//    @Test
//    public void testFindJobContractCardsByClientIdWithNonExistentId() {
//        List<JobContract.ContractState> allStates = Arrays.asList(JobContract.ContractState.values());
//        SimpleJobContractService mockContractService = Mockito.spy(simpleJobContractService);
//        Mockito.doReturn(Collections.emptyList()).when(mockContractService).findByProId(999, allStates, HirenetUtils.ALL_PAGES);
//
//
//        List<JobContractCard> jobContractCards = mockContractService.findJobContractCardsByProId(999, allStates, HirenetUtils.ALL_PAGES, Locale.getDefault());
//
//
//        Assert.assertTrue(jobContractCards.isEmpty());
//    }

//}
