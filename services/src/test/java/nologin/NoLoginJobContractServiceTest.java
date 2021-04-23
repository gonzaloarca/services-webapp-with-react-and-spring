package nologin;

import ar.edu.itba.paw.interfaces.dao.JobContractDao;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.nologin.NoLoginJobContractService;
import ar.edu.itba.paw.services.nologin.NoLoginJobPackageService;
import ar.edu.itba.paw.services.nologin.NoLoginUserService;
import jdk.nashorn.internal.scripts.JO;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class NoLoginJobContractServiceTest {
    private static final User CLIENT = new User(
            3, "manurodriguez@gmail.com", "Manuel Rodriguez",  "0303456", true, true
    );
    private static final User PROFESSIONAL = new User(
            8, "franquesada@gmail.com", "Francisco Quesada",  "0800111333", true, true
    );
    private static final JobPackage JOB_PACKAGE = new JobPackage(
            7, 2, "Arreglo avanzado de plomeria", "Todo tipo de canerias", 200.00, JobPackage.RateType.ONE_TIME, true
    );
    private static final Date CREATION_DATE = new Date();
    private static final String CONTRACT_DESCRIPTION = "Problema en tuberias en la cocina";

    @InjectMocks
    private final NoLoginJobContractService noLoginJobContractService = new NoLoginJobContractService();

    @Mock
    private NoLoginUserService noLoginUserService;

    @Mock
    private NoLoginJobPackageService noLoginJobPackageService;

    @Mock
    private JobContractDao jobContractDao;


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCreateNewClient() {
        Mockito.when(noLoginUserService.findByEmail(Mockito.eq(CLIENT.getEmail())))
                .thenReturn(Optional.of(CLIENT));

        Mockito.when(noLoginJobPackageService.findById(Mockito.eq(JOB_PACKAGE.getId())))
                .thenReturn(JOB_PACKAGE);

        Mockito.when(jobContractDao.create(Mockito.eq(CLIENT.getId()), Mockito.eq(JOB_PACKAGE.getId()),
                Mockito.eq(JOB_PACKAGE.getDescription())))
                .thenReturn(new JobContract(7, CLIENT, JOB_PACKAGE, PROFESSIONAL, CREATION_DATE, CONTRACT_DESCRIPTION));

        JobContract maybeContract = noLoginJobContractService.create(CLIENT.getEmail(),JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription());

        Assert.assertNotNull(maybeContract);
        Assert.assertEquals(CREATION_DATE, maybeContract.getCreationDate());
        Assert.assertEquals(CONTRACT_DESCRIPTION, maybeContract.getDescription());
        Assert.assertEquals(JOB_PACKAGE, maybeContract.getJobPackage());
        Assert.assertEquals(CLIENT, maybeContract.getClient());
        Assert.assertEquals(PROFESSIONAL, maybeContract.getProfessional());
    }

    @Test
    public void testCreateExistingClient() {
        Mockito.when(noLoginJobPackageService.findById(Mockito.eq(JOB_PACKAGE.getId())))
                .thenReturn(JOB_PACKAGE);

        Mockito.when(noLoginUserService.findByEmail(Mockito.eq(CLIENT.getEmail()))).thenReturn(Optional.of(CLIENT));

        Mockito.when(jobContractDao.create(Mockito.eq(CLIENT.getId()), Mockito.eq(JOB_PACKAGE.getId()),
                Mockito.eq(JOB_PACKAGE.getDescription())))
                .thenReturn(new JobContract(7, CLIENT, JOB_PACKAGE, PROFESSIONAL, CREATION_DATE, CONTRACT_DESCRIPTION));

        JobContract maybeContract = noLoginJobContractService.create(CLIENT.getEmail(),JOB_PACKAGE.getId(), JOB_PACKAGE.getDescription());

        Assert.assertNotNull(maybeContract);
        Assert.assertEquals(CREATION_DATE, maybeContract.getCreationDate());
        Assert.assertEquals(CONTRACT_DESCRIPTION, maybeContract.getDescription());
        Assert.assertEquals(JOB_PACKAGE, maybeContract.getJobPackage());
        Assert.assertEquals(CLIENT, maybeContract.getClient());
        Assert.assertEquals(PROFESSIONAL, maybeContract.getProfessional());
    }

    @Test
    public void testCreateWrongPackageID() {
        exceptionRule.expect(RuntimeException.class);

        Mockito.when(noLoginUserService.findByEmail(Mockito.eq(CLIENT.getEmail())))
                .thenReturn(Optional.of(CLIENT));

        Mockito.when(noLoginJobPackageService.findById(Mockito.eq(JOB_PACKAGE.getId()+1))).thenThrow(RuntimeException.class);

        noLoginJobContractService.create(CLIENT.getEmail(),JOB_PACKAGE.getId() + 1, JOB_PACKAGE.getDescription());
    }
}
