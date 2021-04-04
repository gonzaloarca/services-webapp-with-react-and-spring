package nologin;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jdbc.UserDaoJDBC;
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

@RunWith(MockitoJUnitRunner.class)
public class NoLoginUserServiceTest {

    private static final User EXISTING_USER = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "",
            "11-4578-9087",
            true,
            true
    );
    private static final User NEW_USER = new User(
            1,
            "mrodriguez@gmail.com",
            "Manuel Rodriguez",
            "",
            "11-5678-4353",
            false,
            true
    );

    @InjectMocks
    private NoLoginUserService userService = new NoLoginUserService();

    @Mock
    private UserDaoJDBC userDaoJDBC;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testRegisterNewUser() {
        Mockito.when(userDaoJDBC.register(NEW_USER.getEmail(),NEW_USER.getUsername(),NEW_USER.getPhone(),NEW_USER.isProfessional()))
                .thenReturn(NEW_USER);
        User createdUser = userService.register(NEW_USER.getEmail(),NEW_USER.getUsername(),NEW_USER.getPhone(),NEW_USER.isProfessional());
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(NEW_USER,createdUser);
    }

    @Test
    public void testRegisterUserAlreadyCreated(){
        exceptionRule.expect(RuntimeException.class);
        Mockito.when(
                userDaoJDBC.register(
                        Mockito.eq(EXISTING_USER.getEmail()),
                        Mockito.eq(EXISTING_USER.getUsername()),
                        Mockito.eq(EXISTING_USER.getPhone()),
                        Mockito.eq(EXISTING_USER.isProfessional()
                        )
                )
        ).thenThrow(
                new RuntimeException()
        );
        userDaoJDBC.register(EXISTING_USER.getEmail(),EXISTING_USER.getUsername(),EXISTING_USER.getPhone(),EXISTING_USER.isProfessional());
    }


}
