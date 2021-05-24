package simple;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.simple.SimpleJobContractService;
import ar.edu.itba.paw.services.simple.SimpleJobPackageService;
import ar.edu.itba.paw.services.simple.SimpleUserService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobContractServiceTest {

    private static final List<JobPostZone> ZONES =
            new ArrayList<>(Arrays.asList(new JobPostZone(JobPostZone.Zone.values()[1]),
                    new JobPostZone(JobPostZone.Zone.values()[2])));
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
    private final byte[] image1Bytes = {1, 2, 3, 4, 5};
    private final String image1Type = "image/png";

    @InjectMocks
    private final SimpleJobContractService simpleJobContractService = new SimpleJobContractService();

    @Mock
    private SimpleUserService simpleUserService;

    @Mock
    private SimpleJobPackageService simpleJobPackageService;

    @Mock
    private JobContractDao jobContractDao;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCreateNewClient() {
        Mockito.when(simpleUserService.findByEmail(Mockito.eq(CLIENT.getEmail())))
                .thenReturn(Optional.of(CLIENT));

        Mockito.when(jobContractDao.create(Mockito.eq(CLIENT.getId()), Mockito.eq(JOB_PACKAGE.getId()),
                Mockito.eq(JOB_PACKAGE.getDescription())))
                .thenReturn(new JobContract(7, CLIENT, JOB_PACKAGE,
                        CREATION_DATE, CONTRACT_DESCRIPTION, null));

        JobContract maybeContract = simpleJobContractService.create(CLIENT.getEmail(), JOB_PACKAGE.getId(),
                JOB_PACKAGE.getDescription());

        Assert.assertNotNull(maybeContract);
        Assert.assertEquals(CREATION_DATE, maybeContract.getCreationDate());
        Assert.assertEquals(CONTRACT_DESCRIPTION, maybeContract.getDescription());
        Assert.assertEquals(JOB_PACKAGE, maybeContract.getJobPackage());
        Assert.assertEquals(CLIENT, maybeContract.getClient());
        Assert.assertEquals(PROFESSIONAL, maybeContract.getProfessional());
    }

    @Test
    public void testCreateExistingClient() {
        Mockito.when(simpleUserService.findByEmail(Mockito.eq(CLIENT.getEmail()))).thenReturn(Optional.of(CLIENT));

        Mockito.when(jobContractDao.create(Mockito.eq(CLIENT.getId()), Mockito.eq(JOB_PACKAGE.getId()),
                Mockito.eq(JOB_PACKAGE.getDescription())))
                .thenReturn(new JobContract(7, CLIENT, JOB_PACKAGE, CREATION_DATE,
                        CONTRACT_DESCRIPTION, null));

        JobContract maybeContract = simpleJobContractService.create(CLIENT.getEmail(), JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription());

        Assert.assertNotNull(maybeContract);
        Assert.assertEquals(CREATION_DATE, maybeContract.getCreationDate());
        Assert.assertEquals(CONTRACT_DESCRIPTION, maybeContract.getDescription());
        Assert.assertEquals(JOB_PACKAGE, maybeContract.getJobPackage());
        Assert.assertEquals(CLIENT, maybeContract.getClient());
        Assert.assertEquals(PROFESSIONAL, maybeContract.getProfessional());
    }

    @Test
    public void testCreateWithoutImage() {
        Mockito.when(simpleUserService.findByEmail(Mockito.eq(CLIENT.getEmail()))).thenReturn(Optional.of(CLIENT));

        simpleJobContractService.create(CLIENT.getEmail(), JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription());

        Mockito.verify(jobContractDao).create(CLIENT.getId(), JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription());
    }

    @Test
    public void testCreateUserWithImage() {
        Mockito.when(simpleUserService.findByEmail(Mockito.eq(CLIENT.getEmail()))).thenReturn(Optional.of(CLIENT));

        simpleJobContractService.create(CLIENT.getEmail(), JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription(),
                new ByteImage(image1Bytes, image1Type));

        Mockito.verify(jobContractDao).create(CLIENT.getId(), JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription(),
                new ByteImage(image1Bytes, image1Type));
    }
}
