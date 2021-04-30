package simple;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.simple.SimpleJobPostService;
import ar.edu.itba.paw.services.simple.SimpleUserService;
import org.junit.Assert;
import org.junit.Test;
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
public class SimpleJobPostServiceTest {

    private static final User EXISTING_USER = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "11-4578-9087",
            true,
            true,
            LocalDateTime.now());
    private static final User EXISTING_USER_TO_PROF = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "11-4578-9087",
            true,
            true,
            LocalDateTime.now());
    private static final User NEW_PROFESSIONAL = new User(
            1,
            "mrodriguez@gmail.com",
            "Manuel Rodriguez",
            "11-5678-4353",
            true,
            true,
            LocalDateTime.now());
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
            0.0,true,
            LocalDateTime.now());
    private static final JobPost JOB_POST_EXISTING_USER = new JobPost(
            1,
            EXISTING_USER_TO_PROF,
            "Electricista a domicilio",
            "Luna a viernes 10 a 14",
            JobPost.JobType.ELECTRICITY,
            ZONES,
            0.0,true,
            LocalDateTime.now());

    @InjectMocks
    private SimpleJobPostService jobPostService = new SimpleJobPostService();

    @Mock
    private SimpleUserService userService;

    @Mock
    private JobPostDao jobPostDao;

    @Test
    public void testCreatePostNewUser() {
        Mockito.when(userService.findByEmail(NEW_PROFESSIONAL.getEmail()))
                .thenReturn(Optional.of(NEW_PROFESSIONAL));
        Mockito.when(jobPostDao.create(NEW_PROFESSIONAL.getId(), JOB_POST_NEW_USER.getTitle(), JOB_POST_NEW_USER.getAvailableHours(), JOB_POST_NEW_USER.getJobType(),ZONES))
                .thenReturn(JOB_POST_NEW_USER);
        int[] zonesInt = new int[ZONES.size()];
        for (int i = 0; i < ZONES.size(); i++) {
            zonesInt[i] = ZONES.get(i).ordinal();
        }
        JobPost jobPost = jobPostService.create(NEW_PROFESSIONAL.getEmail(), JOB_POST_NEW_USER.getTitle(), JOB_POST_NEW_USER.getAvailableHours(), JOB_POST_NEW_USER.getJobType().ordinal(),zonesInt );

        Assert.assertEquals(jobPost, JOB_POST_NEW_USER);

    }

    @Test
    public void testCreatePostExistingUserNoProf(){
        Mockito.when(userService.findByEmail(EXISTING_USER.getEmail()))
                .thenReturn(Optional.of(EXISTING_USER));
//        Mockito.when(userService.switchRole(EXISTING_USER.getId()))
//                .thenReturn(Optional.of(EXISTING_USER_TO_PROF));
        Mockito.when(jobPostDao.create(EXISTING_USER.getId(), JOB_POST_EXISTING_USER.getTitle(), JOB_POST_EXISTING_USER.getAvailableHours(), JOB_POST_EXISTING_USER.getJobType(),ZONES))
                .thenReturn(JOB_POST_EXISTING_USER);
        int[] zonesInt = new int[ZONES.size()];
        for (int i = 0; i < ZONES.size(); i++) {
            zonesInt[i] = ZONES.get(i).ordinal();
        }
        JobPost jobPost = jobPostService.create(EXISTING_USER.getEmail(),JOB_POST_EXISTING_USER.getTitle(),JOB_POST_EXISTING_USER.getAvailableHours(),JOB_POST_EXISTING_USER.getJobType().ordinal(),zonesInt);

        Assert.assertEquals(JOB_POST_EXISTING_USER,jobPost);
        Assert.assertEquals(0,0);
    }
}
