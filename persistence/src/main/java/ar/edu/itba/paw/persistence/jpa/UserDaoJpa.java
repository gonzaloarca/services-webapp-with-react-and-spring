package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.ByteImage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public User register(String email, String password, String username, String phone) {
        final User user = new User(email,username,phone,true,false,LocalDateTime.now());
    }

    @Override
    public User register(String email, String password, String username, String phone, ByteImage image) {
        return null;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone) {
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone, ByteImage image) {
        return Optional.empty();
    }

    @Override
    public Optional<UserAuth> findAuthInfo(String email) {
        return Optional.empty();
    }

    @Override
    public void assignRole(long id, int role) {

    }

    @Override
    public List<UserAuth.Role> findRoles(long id) {
        return null;
    }

    @Override
    public Optional<User> findUserByRoleAndId(UserAuth.Role role, long id) {
        return Optional.empty();
    }

    @Override
    public void changeUserPassword(long id, String password) {

    }

    @Override
    public void verifyUser(long id) {

    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public List<JobPost.JobType> findUserJobTypes(long id) {
        return null;
    }

    @Override
    public int findUserRankingInJobType(long id, JobPost.JobType jobType) {
        return 0;
    }
}
