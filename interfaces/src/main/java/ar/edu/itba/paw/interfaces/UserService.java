package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(long id);

    User register(String username, String password);
}
