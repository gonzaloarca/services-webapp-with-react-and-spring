package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.UserOriginal;

import java.util.Optional;

public interface UserDaoOriginal {

    Optional<UserOriginal> get(long id);

    UserOriginal register(String name, String password);

    Optional<UserOriginal> findByName(String name);
}
