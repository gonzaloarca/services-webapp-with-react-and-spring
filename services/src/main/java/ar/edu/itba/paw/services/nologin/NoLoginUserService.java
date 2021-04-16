package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoLoginUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User register(String email,String password, String username, String phone, List<Integer> roles) {
        Optional<User> maybeUser = userDao.findByEmail(email);
        if(maybeUser.isPresent()){
            //TODO: arrojar error de el usuario ya existe
        }
        User registeredUser = userDao.register(email,password, username, phone);
        if (registeredUser == null) {
            //TODO: LANAZAR EXCEPCION APROPIADA
            throw new NoSuchElementException();
        }
        roles.forEach(role -> userDao.assignRole(registeredUser.getId(),role));
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

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        return userDao.updateUserByEmail(email, phone, name);
    }

    @Override
    public Optional<User> updateUserByid(long id, String phone, String name){
        return userDao.updateUserByid(id,phone,name);
    }

    @Override
    public Optional<UserAuth> getAuthInfo(String email) {
        return userDao.getAuthInfo(email);
    }


}
