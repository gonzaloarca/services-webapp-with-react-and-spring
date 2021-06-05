package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.VerificationTokenExpiredException;

public interface VerificationTokenService {

	VerificationToken create(User user);

	void verifyToken(User user, String token) throws VerificationTokenExpiredException;

}
