package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(String email,String password, String username, String phone, List<Integer> role);

    User register(String email, String password, String username, String phone, List<Integer> role, ByteImage image);

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
}
