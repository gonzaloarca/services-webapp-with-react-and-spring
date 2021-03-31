package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDaoOriginal;
import ar.edu.itba.paw.interfaces.UserServiceOriginal;
import ar.edu.itba.paw.models.UserOriginal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceOriginal {

    @Autowired
    private UserDaoOriginal userDao;

    @Override
    public Optional<UserOriginal> findById(long id) {
        return userDao.get(id);
    }

    @Override
    public UserOriginal register(String username, String password) {
        return userDao.register(username, password);
    }
}
