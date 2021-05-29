package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User register(String email, String password, String username, String phone);

    User register(String email, String password, String username, String phone, ByteImage image);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    Optional<User> updateUserByEmail(String email, String phone, String name);

    Optional<User> updateUserById(long id, String name, String phone);

    Optional<User> updateUserById(long id, String name, String phone, ByteImage image);

    Optional<UserAuth> findAuthInfo(String email);

    Optional<UserAuth> assignRole(long id, int role);

    List<UserAuth.Role> findRoles(long id);

    Optional<User> findUserByRoleAndId(UserAuth.Role role, long id);

    boolean changeUserPassword(long id, String password);

    boolean verifyUser(long id);

    boolean deleteUser(long id);

    List<JobPost.JobType> findUserJobTypes(long id);

    int findUserRankingInJobType(long id, JobPost.JobType jobType);
}
