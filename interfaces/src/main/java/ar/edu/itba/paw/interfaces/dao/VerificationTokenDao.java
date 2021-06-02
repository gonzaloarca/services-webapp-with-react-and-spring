package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao {

	VerificationToken create(User user);

	Optional<VerificationToken> findByUserId(long userId);

	void deleteToken(long userId);
}
