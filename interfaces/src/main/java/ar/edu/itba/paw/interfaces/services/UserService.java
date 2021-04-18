package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(String email,String password, String username, String phone, List<Integer> role);

    User findById(long id);

    Optional<User> findByEmail(String email);

    User updateUserByEmail(String email,String phone, String name);

    User updateUserByid(long id,String phone, String name);

    User getUserByRoleAndId(int role, long id);

    Optional<UserAuth> getAuthInfo(String email);

    void assignRole(long id, int role);

    List<Review> getProfessionalReviews(long id);

    Double getProfessionalAvgRate(long id);

}
