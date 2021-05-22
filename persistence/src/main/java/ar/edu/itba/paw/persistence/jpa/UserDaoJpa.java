package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User register(String email, String password, String username, String phone) {
        final User user = new User(email, username, phone, true, false, LocalDateTime.now(), password);
        em.persist(user);
        return user;
    }

    @Override
    public User register(String email, String password, String username, String phone, ByteImage image) {
        final User user = new User(email, username, phone, true, false, image, LocalDateTime.now(), password);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final TypedQuery<User> query = em.createQuery("FROM User AS u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        Optional<User> aux = findByEmail(email);
        if(aux.isPresent()) {
            aux.get().setUsername(name);
            aux.get().setPhone(phone);
            em.persist(aux);
        }
        return aux;
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone) {
        User aux = em.find(User.class,id);
        if(aux != null) {
            aux.setUsername(name);
            aux.setPhone(phone);
            em.persist(aux);
        }
        return Optional.ofNullable(aux);
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone, ByteImage image) {
        Optional<User> aux = findById(id);
        if(aux.isPresent()) {
            aux.get().setUsername(name);
            aux.get().setPhone(phone);
            aux.get().setByteImage(image);
            em.persist(aux.get());
        }
        return aux;
    }

    @Override
    public Optional<UserAuth> findAuthInfo(String email) {
        final TypedQuery<UserAuth> query = em.createQuery("FROM UserAuth AS u WHERE u.email = :email", UserAuth.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
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
