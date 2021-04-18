package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoLoginUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User register(String email, String username, String phone, boolean isProfessional) {
        User registeredUser = userDao.register(email, username, phone, isProfessional);
        if (registeredUser == null) {
            //TODO: LANAZAR EXCEPCION APROPIADA
            throw new NoSuchElementException();
        }
        return registeredUser;
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /*
    @Override
    public Optional<User> switchRole(long id) {
        return userDao.switchRole(id);
    }

     */

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        return userDao.updateUserByEmail(email, phone, name);
    }

    @Override
    public Optional<User> updateUserByid(long id, String phone, String name){
        return userDao.updateUserByid(id,phone,name);
    }
}
