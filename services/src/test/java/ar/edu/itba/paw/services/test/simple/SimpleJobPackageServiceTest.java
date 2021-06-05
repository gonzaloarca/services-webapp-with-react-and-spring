package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.simple.SimpleJobPackageService;
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

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobPackageServiceTest {
    private static final List<JobPost.Zone> ZONES =
            new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));
    private static final User PROFESSIONAL = new User(
            8, "franquesada@gmail.com", "Francisco Quesada",  "0800111333", true, true, LocalDateTime.now());
    private static final JobPost JOB_POST = new JobPost(
            2, PROFESSIONAL, "Plomero matriculado", "Lunes - Jueves de 09 a 16hrs", JobPost.JobType.PLUMBING,
            ZONES, true, LocalDateTime.now());
    private static final JobPackage JOB_PACKAGE = new JobPackage(
            7, JOB_POST, "Arreglos menores", "Canerias rotas", 200.00, JobPackage.RateType.ONE_TIME, true);


    @InjectMocks
    SimpleJobPackageService simpleJobPackageService = new SimpleJobPackageService();

    @Mock
    JobPackageDao jobPackageDao;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createSuccess() {
        Mockito.when(jobPackageDao.create(JOB_PACKAGE.getPostId(), JOB_PACKAGE.getTitle(), JOB_PACKAGE.getDescription(),
                JOB_PACKAGE.getPrice(), JOB_PACKAGE.getRateType())).thenReturn(JOB_PACKAGE);

        JobPackage maybePackage = simpleJobPackageService.create(JOB_PACKAGE.getPostId(), JOB_PACKAGE.getTitle(), JOB_PACKAGE.getDescription(),
                JOB_PACKAGE.getPrice().toString(), JOB_PACKAGE.getRateType().getValue());

        Assert.assertNotNull(maybePackage);
        Assert.assertEquals(JOB_PACKAGE, maybePackage);
    }

}
