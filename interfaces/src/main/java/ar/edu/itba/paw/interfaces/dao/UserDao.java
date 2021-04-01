package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserDao {

    User register(String email, String username, String phone, boolean isProfessional);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    void switchRole(long id);
}
