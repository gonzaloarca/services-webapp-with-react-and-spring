package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao {

	VerificationToken create(long user_id);

	Optional<VerificationToken> findByUserId(long user_id);

	void deleteToken(long user_id);
}
