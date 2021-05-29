package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao {

	VerificationToken create(long userId);

	Optional<VerificationToken> findByUserId(long userId);

	void deleteToken(long userId);
}
