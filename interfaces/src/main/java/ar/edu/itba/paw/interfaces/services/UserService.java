package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotVerifiedException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //TODO eliminar este metodo cambiando los tests
    User register(String email,String password, String username, String phone);

    User register(String email, String password, String username, String phone, ByteImage image)
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

    //TODO: TEST
    List<JobPost.JobType> findUserJobTypes(long id);

    //TODO: TEST
    int findUserRankingInJobType(long id, JobPost.JobType jobType);
}
