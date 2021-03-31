package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.UserOriginal;

import java.util.Optional;

public interface UserServiceOriginal {

    Optional<UserOriginal> findById(long id);

    UserOriginal register(String username, String password);
}
