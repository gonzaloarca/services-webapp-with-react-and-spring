package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.interfaces.services.TokenService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotVerifiedException;
import ar.edu.itba.paw.persistence.jpa.UserDaoJpa;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SimpleUserServiceTest {

    private static final User EXISTING_USER = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "11-4578-9087",
            true,
            true,
            LocalDateTime.now());
    private static final User EXISTING_UNVERIFIED_USER = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "11-4578-9087",
            true,
            false,
            LocalDateTime.now());
    private static final User NEW_USER = new User(
            1,
            "mrodriguez@gmail.com",
            "Manuel Rodriguez",
            "11-5678-4353",
            true,
            false,
            LocalDateTime.now());
    private static final VerificationToken TOKEN = new VerificationToken("", NEW_USER, Instant.now());
    private final byte[] image1Bytes = {1, 2, 3, 4, 5};
    private final String image1Type = "image/png";
    private final ByteImage byteImage = new ByteImage(image1Bytes, image1Type);
    private final String PASSWORD = "password";

    @InjectMocks
    private SimpleUserService userService = new SimpleUserService();

    @Mock
    private MailingService mailingService;  //necesario que este como Mock para el testRegisterNewUser

    @Mock
    private UserDaoJpa userDaoJpa;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService verificationTokenService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testRegisterNewUser() {
        Mockito.when(userDaoJpa.register(Mockito.eq(NEW_USER.getEmail()), Mockito.eq(""), Mockito.eq(NEW_USER.getUsername()),
                Mockito.eq(NEW_USER.getPhone()))).thenReturn(NEW_USER);
        Mockito.when(passwordEncoder.encode(Mockito.eq(""))).thenReturn("");
        Mockito.when(verificationTokenService.createVerificationToken(Mockito.eq(NEW_USER))).thenReturn(TOKEN);

        User createdUser = userService.register(NEW_USER.getEmail(), "", NEW_USER.getUsername(), NEW_USER.getPhone(), null, Locale.getDefault(),"");
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(NEW_USER, createdUser);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testRegisterUserAlreadyCreatedAndVerified() {
        Mockito.when(userDaoJpa.findByEmail(Mockito.eq(EXISTING_USER.getEmail())))
                .thenReturn(Optional.of(EXISTING_USER));

        userService.register(EXISTING_USER.getEmail(), PASSWORD, EXISTING_USER.getUsername(), EXISTING_USER.getPhone(),
                byteImage, Locale.getDefault(),"");
    }

    @Test()
    public void testRegisterUserNotVerified() {
        Mockito.when(userDaoJpa.findByEmail(Mockito.eq(EXISTING_UNVERIFIED_USER.getEmail())))
                .thenReturn(Optional.of(EXISTING_UNVERIFIED_USER));
        Mockito.when(userDaoJpa.register(Mockito.eq(EXISTING_UNVERIFIED_USER.getEmail()), Mockito.eq(""), Mockito.eq(EXISTING_UNVERIFIED_USER.getUsername()),
                Mockito.eq(EXISTING_UNVERIFIED_USER.getPhone()))).thenReturn(EXISTING_UNVERIFIED_USER);
        Mockito.when(passwordEncoder.encode(Mockito.eq(""))).thenReturn("");
        Mockito.when(verificationTokenService.createVerificationToken(Mockito.eq(EXISTING_UNVERIFIED_USER))).thenReturn(TOKEN);

        User createdUser = userService.register(EXISTING_UNVERIFIED_USER.getEmail(), "", EXISTING_UNVERIFIED_USER.getUsername(), EXISTING_UNVERIFIED_USER.getPhone(), null, Locale.getDefault(),"");
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(EXISTING_UNVERIFIED_USER, createdUser);
    }

    @Test
    public void testRegisterUserWithoutImage() {
        Mockito.when(passwordEncoder.encode(Mockito.eq(""))).thenReturn("");

        userService.register(NEW_USER.getEmail(), "", NEW_USER.getUsername(), EXISTING_USER.getPhone(), null, Locale.getDefault(),"");

        Mockito.verify(userDaoJpa).register(NEW_USER.getEmail(), "", NEW_USER.getUsername(),
                EXISTING_USER.getPhone());
    }

    @Test
    public void testRegisterUserWithImage() {
        Mockito.when(passwordEncoder.encode(Mockito.eq(""))).thenReturn("");

        userService.register(NEW_USER.getEmail(), "", NEW_USER.getUsername(), EXISTING_USER.getPhone(),
                new ByteImage(image1Bytes, image1Type), Locale.getDefault(),"");

        Mockito.verify(userDaoJpa).register(NEW_USER.getEmail(), "", NEW_USER.getUsername(),
                EXISTING_USER.getPhone(), new ByteImage(image1Bytes, image1Type));
    }

}
