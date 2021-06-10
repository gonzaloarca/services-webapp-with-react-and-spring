package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.dao.RecoveryTokenDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.dao.VerificationTokenDao;
import ar.edu.itba.paw.models.RecoveryToken;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.MismatchedTokensException;
import ar.edu.itba.paw.services.simple.SimpleTokenService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SimpleTokenServiceTest {

    private static final User USER = new User(
            1,
            "fquesada@gmail.com",
            "Francisco Quesada",
            "11-4578-9087",
            true,
            true,
            LocalDateTime.now());

    private static final User NOT_VERIFIED_USER = new User(
            2,
            "mrodriguez@gmail.com",
            "Manuel Rodriguez",
            "11-5678-4353",
            true,
            false,
            LocalDateTime.now());

    private static final String REAL_TOKEN = "ABCDEFGHIJK";

    private static final String INVALID_TOKEN = "123456789";

    private static final VerificationToken VERIFICATION_TOKEN = new VerificationToken(
            REAL_TOKEN,
            USER,
            Instant.now()
    );

    private static final VerificationToken OLD_VERIFICATION_TOKEN = new VerificationToken(
            REAL_TOKEN,
            USER,
            Instant.MIN
    );

    private static final RecoveryToken RECOVERY_TOKEN = new RecoveryToken(
            USER,
            REAL_TOKEN,
            Instant.now()
    );

    private static final RecoveryToken OLD_RECOVERY_TOKEN = new RecoveryToken(
            USER,
            REAL_TOKEN,
            Instant.MIN
    );

    @InjectMocks
    SimpleTokenService simpleTokenService;

    @Mock
    private VerificationTokenDao verificationTokenDao;

    @Mock
    private RecoveryTokenDao recoveryTokenDao;

    @Mock
    private UserDao userDao;

    @Test
    public void createVerificationTokenSuccessTest() {
        Mockito.when(verificationTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.empty());

        Mockito.when(verificationTokenDao.create(Mockito.eq(USER)))
                .thenReturn(VERIFICATION_TOKEN);

        VerificationToken token = simpleTokenService.createVerificationToken(USER);

        Assert.assertNotNull(token);
        Assert.assertEquals(VERIFICATION_TOKEN, token);
    }

    @Test
    public void createVerificationTokenExistingTokenTest() {
        Mockito.when(verificationTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(VERIFICATION_TOKEN));

        Mockito.when(verificationTokenDao.create(Mockito.eq(USER)))
                .thenReturn(VERIFICATION_TOKEN);

        VerificationToken token = simpleTokenService.createVerificationToken(USER);

        Assert.assertNotNull(token);
        Assert.assertEquals(VERIFICATION_TOKEN, token);
    }

    @Test
    public void createRecoveryTokenSuccessTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.empty());

        Mockito.when(recoveryTokenDao.create(Mockito.eq(USER)))
                .thenReturn(RECOVERY_TOKEN);

        RecoveryToken token = simpleTokenService.createRecoveryToken(USER);

        Assert.assertNotNull(token);
        Assert.assertEquals(RECOVERY_TOKEN, token);
    }

    @Test
    public void createRecoveryTokenExistingTokenTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(RECOVERY_TOKEN));

        Mockito.when(recoveryTokenDao.create(Mockito.eq(USER)))
                .thenReturn(RECOVERY_TOKEN);

        RecoveryToken token = simpleTokenService.createRecoveryToken(USER);

        Assert.assertNotNull(token);
        Assert.assertEquals(RECOVERY_TOKEN, token);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createRecoveryTokenNotVerifiedTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(NOT_VERIFIED_USER.getId())))
                .thenReturn(Optional.empty());

        simpleTokenService.createRecoveryToken(NOT_VERIFIED_USER);
    }

    @Test
    public void verifyVerificationTokenSuccessTest() {
        Mockito.when(verificationTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(VERIFICATION_TOKEN));

        Assert.assertTrue(simpleTokenService.verifyVerificationToken(USER, REAL_TOKEN));
    }

    @Test(expected = NoSuchElementException.class)
    public void verifyVerificationTokenAbsentTokenTest() {
        Mockito.when(verificationTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.empty());

        simpleTokenService.verifyVerificationToken(USER, REAL_TOKEN);
    }

    @Test
    public void verifyVerificationTokenExpiredTest() {
        Mockito.when(verificationTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(OLD_VERIFICATION_TOKEN));

        Assert.assertFalse(simpleTokenService.verifyVerificationToken(USER, REAL_TOKEN));
    }

    @Test(expected = MismatchedTokensException.class)
    public void verifyVerificationTokenMismatchedTest() {
        Mockito.when(verificationTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(VERIFICATION_TOKEN));

        simpleTokenService.verifyVerificationToken(USER, INVALID_TOKEN);
    }

    @Test
    public void verifyRecoveryTokenSuccessTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(RECOVERY_TOKEN));

        Assert.assertTrue(simpleTokenService.verifyRecoveryToken(USER.getId(), REAL_TOKEN));
    }

    @Test(expected = NoSuchElementException.class)
    public void verifyRecoveryTokenAbsentTokenTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.empty());

        simpleTokenService.verifyRecoveryToken(USER.getId(), REAL_TOKEN);
    }

    @Test
    public void verifyRecoveryTokenExpiredTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(OLD_RECOVERY_TOKEN));

        Assert.assertFalse(simpleTokenService.verifyRecoveryToken(USER.getId(), REAL_TOKEN));
    }

    @Test(expected = MismatchedTokensException.class)
    public void verifyRecoveryTokenMismatchedTest() {
        Mockito.when(recoveryTokenDao.findByUserId(Mockito.eq(USER.getId())))
                .thenReturn(Optional.of(RECOVERY_TOKEN));

        simpleTokenService.verifyRecoveryToken(USER.getId(), INVALID_TOKEN);
    }

}
