package nologin;

import ar.edu.itba.paw.interfaces.dao.JobPackageDao;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.nologin.NoLoginJobPackageService;
import ar.edu.itba.paw.services.nologin.NoLoginJobPostService;
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
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class NoLoginJobPackageServiceTest {
    private static final JobPackage JOB_PACKAGE = new JobPackage(
            7, 2, "Arreglos menores", "Canerias rotas", 200.00, JobPackage.RateType.ONE_TIME, true);
    private static final User PROFESSIONAL = new User(
            8, "franquesada@gmail.com", "Francisco Quesada", "", "0800111333", false, true);
    private static final JobPost JOB_POST = new JobPost(
            JOB_PACKAGE.getPostId(), PROFESSIONAL, "Plomero matriculado", "Lunes - Jueves de 09 a 16hrs", JobPost.JobType.PLUMBING,
            Arrays.asList(JobPost.Zone.BELGRANO, JobPost.Zone.PALERMO), true);

    @InjectMocks
    NoLoginJobPackageService noLoginJobPackageService = new NoLoginJobPackageService();

    @Mock
    JobPackageDao jobPackageDao;

    @Mock
    NoLoginJobPostService noLoginJobPostService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createSuccess() {
        Mockito.when(noLoginJobPostService.findById(Mockito.eq(JOB_PACKAGE.getPostId())))
                .thenReturn(JOB_POST);

        Mockito.when(jobPackageDao.create(JOB_PACKAGE.getPostId(), JOB_PACKAGE.getTitle(), JOB_PACKAGE.getDescription(),
                JOB_PACKAGE.getPrice(), JOB_PACKAGE.getRateType())).thenReturn(JOB_PACKAGE);

        JobPackage maybePackage = noLoginJobPackageService.create(JOB_PACKAGE.getPostId(), JOB_PACKAGE.getTitle(), JOB_PACKAGE.getDescription(),
                JOB_PACKAGE.getPrice(), JOB_PACKAGE.getRateType());

        Assert.assertNotNull(maybePackage);
        Assert.assertEquals(JOB_PACKAGE, maybePackage);
    }

    @Test
    public void createNoExistingPostId() {
        exceptionRule.expect(RuntimeException.class);

        noLoginJobPackageService.create(JOB_PACKAGE.getId() + 1, JOB_PACKAGE.getTitle(), JOB_PACKAGE.getDescription(),
                JOB_PACKAGE.getPrice(), JOB_PACKAGE.getRateType());
    }

}
