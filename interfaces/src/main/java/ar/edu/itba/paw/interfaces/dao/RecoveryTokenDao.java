package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.RecoveryToken;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.TokenNotFoundException;

import java.util.Optional;

public interface RecoveryTokenDao {

    RecoveryToken create(User user);

    Optional<RecoveryToken> findByUserId(long userId);

    void deleteToken(long userId) throws TokenNotFoundException;
}
