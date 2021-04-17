package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User register(String email, String username, String phone, boolean isProfessional);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

//    Optional<User> switchRole(long id);

    Optional<User> updateUserByEmail(String email,String phone, String name);

    Optional<User> updateUserByid(long id,String phone, String name);

    Optional<List<Review>> findUserReviews(long id);
}
