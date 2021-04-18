package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    User register(String email, String username, String phone, boolean isProfessional);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

//    Optional<User> switchRole(long id);

    Optional<User> updateUserByEmail(String email,String phone, String name);

    Optional<User> updateUserByid(long id,String phone, String name);

}
