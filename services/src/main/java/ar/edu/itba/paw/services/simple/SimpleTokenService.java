package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.dao.RecoveryTokenDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.dao.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.TokenService;
import ar.edu.itba.paw.models.RecoveryToken;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.MismatchedTokensException;
import ar.edu.itba.paw.models.exceptions.ExpiredTokenException;
import ar.edu.itba.paw.models.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class SimpleTokenService implements TokenService {

	private final static int AVAILABLE_TIME = 24 * 60 * 60;

	@Autowired
	private VerificationTokenDao verificationTokenDao;

	@Autowired
	private RecoveryTokenDao recoveryTokenDao;

	@Autowired
	private UserDao userDao;

	@Override
	public VerificationToken createVerificationToken(User user) {
		Optional<VerificationToken> possibleToken = verificationTokenDao.findByUserId(user.getId());

		if(possibleToken.isPresent()) {
			verificationTokenDao.deleteToken(user.getId());
		}

		return verificationTokenDao.create(user);
	}

	@Override
	public RecoveryToken createRecoveryToken(User user) {
		Optional<RecoveryToken> tokenOptional = recoveryTokenDao.findByUserId(user.getId());

		if(tokenOptional.isPresent())
			recoveryTokenDao.deleteToken(user.getId());

		if(!user.isVerified())
			throw new UnsupportedOperationException("User must be verified to recover account");

		return recoveryTokenDao.create(user);
	}

	@Override
	public boolean verifyVerificationToken(User user, String token) throws ExpiredTokenException {
		Optional<VerificationToken> optionalToken = verificationTokenDao.findByUserId(user.getId());

		if(!optionalToken.isPresent())
			throw new NoSuchElementException();

		try {
			verifyToken(token, optionalToken.get().getToken(), optionalToken.get().getCreationDate());
		} catch (ExpiredTokenException e) {
			verificationTokenDao.deleteToken(user.getId());
			return false;
		}

		verificationTokenDao.deleteToken(user.getId());
		userDao.verifyUser(user.getId());
		return true;
	}

	@Override
	public boolean verifyRecoveryToken(long user_id, String token) throws ExpiredTokenException {
		Optional<RecoveryToken> tokenOptional = recoveryTokenDao.findByUserId(user_id);

		if(!tokenOptional.isPresent())
			throw new NoSuchElementException();

		try {
			verifyToken(token, tokenOptional.get().getToken(), tokenOptional.get().getCreationDate());
		} catch (ExpiredTokenException e) {
			recoveryTokenDao.deleteToken(user_id);
			return false;
		}

		// En este caso si es válido todavia no lo borro, espero a que cambien la contraseña
		return true;
	}

	@Override
	public void deleteRecoveryToken(long user_id) throws TokenNotFoundException {
		recoveryTokenDao.deleteToken(user_id);
	}

	private void verifyToken(String urlToken, String actualToken, Instant creationDate)
			throws ExpiredTokenException, MismatchedTokensException {
		long createTime = creationDate.getEpochSecond();
		long currentTime = Instant.now().getEpochSecond();

		if (!urlToken.equals(actualToken))
			throw new MismatchedTokensException();

		if (currentTime - createTime > AVAILABLE_TIME)
			throw new ExpiredTokenException();

	}
}
