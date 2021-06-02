package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotVerifiedException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User register(String email, String password, String username, String phone, ByteImage image, Locale locale)
            throws UserAlreadyExistsException, UserNotVerifiedException;

    User findById(long id);

    Optional<User> findByEmail(String email);

    User updateUserByEmail(String email,String phone, String name);

    User updateUserById(long id, String name, String phone);

    User updateUserById(long id, String name, String phone, ByteImage byteImage);

    User getUserByRoleAndId(int role, long id);

    Optional<UserAuth> getAuthInfo(String email);

    void assignRole(long id, int role);

    boolean validCredentials(String email, String password);

    void changeUserPassword(String email, String password);

    List<JobPost.JobType> findUserJobTypes(long id);

    int findUserRankingInJobType(long id, JobPost.JobType jobType);

    List<AnalyticRanking> findUserAnalyticRankings(long id);

    void recoverUserPassword(String email, Locale locale);

    UserWithImage findUserWithImage(long id);

    ByteImage findImageByUserId(long id);
}
