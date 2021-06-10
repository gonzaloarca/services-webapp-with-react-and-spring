package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.RecoveryToken;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.ExpiredTokenException;
import ar.edu.itba.paw.models.exceptions.TokenNotFoundException;

public interface TokenService {

	VerificationToken createVerificationToken(User user);

	RecoveryToken createRecoveryToken(User user);

	boolean verifyVerificationToken(User user, String token) throws ExpiredTokenException;

	boolean verifyRecoveryToken(long user_id, String token) throws ExpiredTokenException;

	void deleteRecoveryToken(long user_id) throws TokenNotFoundException;

}
