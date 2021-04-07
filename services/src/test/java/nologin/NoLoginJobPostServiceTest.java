package nologin;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.nologin.NoLoginJobPostService;
import ar.edu.itba.paw.services.nologin.NoLoginUserService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class NoLoginJobPostServiceTest {

    private static final User EXISTING_USER = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "",
            "11-4578-9087",
            false,
            true
    );
    private static final User EXISTING_USER_TO_PROF = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "",
            "11-4578-9087",
            true,
            true
    );
    private static final User NEW_PROFESSIONAL = new User(
            1,
            "mrodriguez@gmail.com",
            "Manuel Rodriguez",
            "",
            "11-5678-4353",
            true,
            true
    );
    private static final List<JobPost.Zone> ZONES = new ArrayList<>(
            Arrays.asList(
                    JobPost.Zone.PALERMO,
                    JobPost.Zone.BELGRANO
            )
    );
    private static final JobPost JOB_POST_NEW_USER = new JobPost(
            1,
            NEW_PROFESSIONAL,
            "Electricista a domicilio",
            "Luna a viernes 10 a 14",
            JobPost.JobType.ELECTRICITY,
            ZONES,
            true
    );
    private static final JobPost JOB_POST_EXISTING_USER = new JobPost(
            1,
            EXISTING_USER_TO_PROF,
            "Electricista a domicilio",
            "Luna a viernes 10 a 14",
            JobPost.JobType.ELECTRICITY,
            ZONES,
            true
    );

    @InjectMocks
    private NoLoginJobPostService jobPostService = new NoLoginJobPostService();

    @Mock
    private NoLoginUserService userService;

    @Mock
    private JobPostDao jobPostDao;

    @Test
    public void testCreatePostNewUser() {
        Mockito.when(userService.findByEmail(NEW_PROFESSIONAL.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(userService.register(NEW_PROFESSIONAL.getEmail(),NEW_PROFESSIONAL.getUsername(),NEW_PROFESSIONAL.getPhone(),NEW_PROFESSIONAL.isProfessional()))
                .thenReturn(NEW_PROFESSIONAL);
        Mockito.when(jobPostDao.create(NEW_PROFESSIONAL.getId(), JOB_POST_NEW_USER.getTitle(), JOB_POST_NEW_USER.getAvailableHours(), JOB_POST_NEW_USER.getJobType(),ZONES))
                .thenReturn(JOB_POST_NEW_USER);

        JobPost jobPost = jobPostService.create(NEW_PROFESSIONAL.getEmail(),NEW_PROFESSIONAL.getUsername(),NEW_PROFESSIONAL.getPhone(), JOB_POST_NEW_USER.getTitle(), JOB_POST_NEW_USER.getAvailableHours(), JOB_POST_NEW_USER.getJobType(),ZONES);

        Assert.assertEquals(jobPost, JOB_POST_NEW_USER);

    }

    @Test
    public void testCreatePostExistingUserNoProf(){
        Mockito.when(userService.findByEmail(EXISTING_USER.getEmail()))
                .thenReturn(Optional.of(EXISTING_USER));
        Mockito.when(userService.switchRole(EXISTING_USER.getId()))
                .thenReturn(Optional.of(EXISTING_USER_TO_PROF));
        Mockito.when(jobPostDao.create(EXISTING_USER.getId(), JOB_POST_EXISTING_USER.getTitle(), JOB_POST_EXISTING_USER.getAvailableHours(), JOB_POST_EXISTING_USER.getJobType(),ZONES))
                .thenReturn(JOB_POST_EXISTING_USER);

        JobPost jobPost = jobPostService.create(EXISTING_USER.getEmail(),EXISTING_USER.getUsername(),EXISTING_USER.getPhone(),JOB_POST_EXISTING_USER.getTitle(),JOB_POST_EXISTING_USER.getAvailableHours(),JOB_POST_EXISTING_USER.getJobType(),ZONES);

        Assert.assertEquals(JOB_POST_EXISTING_USER,jobPost);
        Assert.assertEquals(JOB_POST_EXISTING_USER.getUser().isProfessional(),jobPost.getUser().isProfessional());
    }
}
