package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
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
        return em.createQuery("FROM User AS u WHERE u.email = :email", User.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        Optional<User> aux = findByEmail(email);
        if (aux.isPresent()) {
            aux.get().setUsername(name);
            aux.get().setPhone(phone);
            em.persist(aux);
        }
        return aux;
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone) {
        User aux = em.find(User.class, id);
        if (aux != null) {
            aux.setUsername(name);
            aux.setPhone(phone);
            em.persist(aux);
        }
        return Optional.ofNullable(aux);
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone, ByteImage image) {
        Optional<User> aux = findById(id);
        if (aux.isPresent()) {
            aux.get().setUsername(name);
            aux.get().setPhone(phone);
            aux.get().setByteImage(image);
            em.persist(aux.get());
        }
        return aux;
    }

    @Override
    public Optional<UserAuth> findAuthInfo(String email) {
        return em.createQuery("FROM UserAuth AS u WHERE u.email = :email", UserAuth.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserAuth> assignRole(long id, int role) {
        UserAuth userAuth = em.find(UserAuth.class, id);
        UserRole aux_role = new UserRole(UserRole.Role.values()[role]);
        if (userAuth != null && !userAuth.getRoles().contains(aux_role)) {
            userAuth.getRoles().add(aux_role);
            em.persist(userAuth);
        }
        return Optional.ofNullable(userAuth);
    }

    @Override
    public List<UserRole.Role> findRoles(long id) {
        return em.createQuery("FROM UserAuth AS u WHERE u.id = :id", UserRole.Role.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public Optional<User> findUserByRoleAndId(UserRole.Role role, long id) {
        return em.createQuery("FROM User AS u WHERE u.id = :user_id AND u.role_id = :role_id", User.class)
                .setParameter("user_id", id).setParameter("role_id", role.ordinal()).
                        getResultList().stream().findFirst();
    }

    @Override
    public boolean changeUserPassword(long id, String password) {
        User aux = em.find(User.class, id);
        if (aux != null) {
            aux.setPassword(password);
            em.persist(aux);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyUser(long id) {
        User aux = em.find(User.class, id);
        if (aux != null) {
            aux.setVerified(true);
            em.persist(aux);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            user.setActive(false);
            em.persist(user);
            return true;
        }
        return false;
    }

    @Override
    public List<JobPost.JobType> findUserJobTypes(long id) {
        //TODO: CUANDO JOBPOST ESTE HIBERNADO
        return null;
    }

    @Override
    public int findUserRankingInJobType(long id, JobPost.JobType jobType) {
        //TODO: CUANDO JOBPOST ESTE HIBERNADO
        return 0;
    }
}
