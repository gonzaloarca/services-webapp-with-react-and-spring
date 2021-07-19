package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotVerifiedException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    List<UserWithImage> findAllWithImage(int page);

    int findAllWithImageMaxPage();

    User register(String email, String password, String username, String phone, ByteImage image, Locale locale, String webpageUrl)
            throws UserAlreadyExistsException, UserNotVerifiedException;

    User findById(long id);

    Optional<User> findByEmail(String email);

    User updateUserByEmail(String email,String phone, String name);

    UserWithImage updateUserById(long id, String name, String phone);

    User updateUserById(long id, String name, String phone, ByteImage byteImage);

    User getUserByRoleAndId(int role, long id);

    Optional<UserAuth> getAuthInfo(String email);

    void assignRole(long id, int role);

    boolean validCredentials(String email, String password);

    void changeUserPassword(long id, String newPassword, String oldPassword);

    List<JobPost.JobType> findUserJobTypes(long id);

    int findUserRankingInJobType(long id, JobPost.JobType jobType);

    List<AnalyticRanking> findUserAnalyticRankings(long id);

    void recoverUserAccount(String email, Locale locale,String webpageUrl);

    void recoverUserPassword(long user_id, String password);

    UserWithImage findUserWithImage(long id);

    ByteImage findImageByUserId(long id);

    long updateUserImage(long id, ByteImage userImage);

    Optional<UserWithImage> findUserWithImageByEmail(String email);
}
