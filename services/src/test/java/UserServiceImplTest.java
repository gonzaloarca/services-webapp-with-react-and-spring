import ar.edu.itba.paw.interfaces.UserDaoOriginal;
import ar.edu.itba.paw.models.UserOriginal;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "passwordpassword";
    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @Mock
    private UserDaoOriginal mockDao;

    @Test
    public void testCreate() {
// 1. Setup!
        Mockito.when(mockDao.register(Mockito.eq(USERNAME), Mockito.eq(PASSWORD)))
                .thenReturn(new UserOriginal(1, USERNAME, PASSWORD));
// 2. "ejercito" la class under test
        UserOriginal maybeUser = userService.register(USERNAME, PASSWORD);
// 3. Asserts!
        Assert.assertNotNull(maybeUser);
        Assert.assertEquals(USERNAME, maybeUser.getName());
        Assert.assertEquals(PASSWORD, maybeUser.getPassword());
    }

    @Test
    public void testCreateEmptyPassword() {
// 1. Setup!
// 2. "ejercito" la class under test
        UserOriginal maybeUser
                = userService.register(USERNAME, "");
        // 3. Asserts!
        Assert.assertNull(maybeUser);
    }

}
