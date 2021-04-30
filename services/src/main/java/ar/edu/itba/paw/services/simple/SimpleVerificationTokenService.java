package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.dao.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.VerificationTokenService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.VerificationToken;
import exceptions.VerificationTokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SimpleVerificationTokenService implements VerificationTokenService {

	private final static int AVAILABLE_TIME = 24 * 60 * 60;

	@Autowired
	private VerificationTokenDao verificationTokenDao;

	@Autowired
	private UserDao userDao;

	@Override
	public VerificationToken create(User user) {
		Optional<VerificationToken> possibleToken = verificationTokenDao.findByUserId(user.getId());

		if(possibleToken.isPresent()) {
			verificationTokenDao.deleteToken(user.getId());
		}

		return verificationTokenDao.create(user.getId());
	}

	@Override
	public void verifyToken(User user, String token) throws VerificationTokenExpiredException {
		Optional<VerificationToken> possibleToken = verificationTokenDao.findByUserId(user.getId());

		if(possibleToken.isPresent()) {

			VerificationToken trueToken = possibleToken.get();
			long createTime = trueToken.getCreationDate().getEpochSecond();
			long currentTime = Instant.now().getEpochSecond();

			if (token.equals(trueToken.getToken())) {
				if (currentTime - createTime > AVAILABLE_TIME) {
					verificationTokenDao.deleteToken(user.getId());
					throw new VerificationTokenExpiredException();
				}
				verificationTokenDao.deleteToken(user.getId());
				userDao.verifyUser(user.getId());
				userDao.assignRole(user.getId(), UserAuth.Role.CLIENT.ordinal());
				return;
			}
		}

		throw new NoSuchElementException();
	}
}
