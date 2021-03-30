package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> get(long id);

    User register(String name, String password);

    Optional<User> findByName(String name);
}
