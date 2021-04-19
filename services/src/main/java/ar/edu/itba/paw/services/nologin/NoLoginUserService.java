package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoLoginUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User register(String email, String password, String username, String phone, List<Integer> roles,
                         ByteImage image) {
        Optional<User> maybeUser = userDao.findByEmail(email);
        if (maybeUser.isPresent()) {
            //TODO: arrojar error de el usuario ya existe
        }
        User registeredUser = userDao.register(email, password, username, phone, image);
        if (registeredUser == null) {
            //TODO: LANAZAR EXCEPCION APROPIADA
            throw new NoSuchElementException();
        }
        roles.forEach(role -> userDao.assignRole(registeredUser.getId(), role));
        return registeredUser;
    }

    @Override
    public User register(String email, String password, String username, String phone, List<Integer> role) {
        return register(email, password, username, phone, role, null);
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User updateUserByEmail(String email, String phone, String name) {
        return userDao.updateUserByEmail(email, phone, name).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User updateUserByid(long id, String phone, String name) {
        return userDao.updateUserById(id, phone, name).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User getUserByRoleAndId(int role, long id) {
        return userDao.findUserByRoleAndId(UserAuth.Role.values()[role], id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<UserAuth> getAuthInfo(String email) {
        return userDao.findAuthInfo(email);
    }

    @Override
    public void assignRole(long id, int role) {
        List<UserAuth.Role> roles = userDao.findRoles(id);
        if (!roles.contains(UserAuth.Role.values()[role]))
            userDao.assignRole(id, role);
    }

}
