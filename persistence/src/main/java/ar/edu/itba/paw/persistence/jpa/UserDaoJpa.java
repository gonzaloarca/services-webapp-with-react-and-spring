package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.utils.ImageDataConverter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User register(String email, String password, String username, String phone) {
        return register(email, password, username, phone, null);
    }

    @Override
    public User register(String email, String password, String username, String phone, ByteImage image) {
        EncodedImage encodedImage = new EncodedImage(null, null);
        if(image != null)
            encodedImage = new EncodedImage(ImageDataConverter.getEncodedString(image.getData()), image.getType());

        final User user = new User(email, username, phone, true, false, image,  encodedImage,
                LocalDateTime.now(), password);
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return addEncodedImage(Optional.ofNullable(em.find(User.class, id)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return addEncodedImage(em.createQuery("FROM User AS u WHERE u.email = :email", User.class)
                .setParameter("email", email).getResultList().stream().findFirst());
    }

    @Override
    public Optional<User> updateUserByEmail(String email, String phone, String name) {
        Optional<User> aux = findByEmail(email);
        if (aux.isPresent()) {
            aux.get().setUsername(name);
            aux.get().setPhone(phone);
            em.persist(aux);
        }
        return addEncodedImage(aux);
    }

    @Override
    public Optional<User> updateUserById(long id, String name, String phone) {
        User aux = em.find(User.class, id);
        if (aux != null) {
            aux.setUsername(name);
            aux.setPhone(phone);
            em.persist(aux);
        }
        return addEncodedImage(Optional.ofNullable(aux));
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
        return addEncodedImage(aux);
    }

    @Override
    public Optional<UserAuth> findAuthInfo(String email) {
        return em.createQuery("FROM UserAuth AS u JOIN FETCH u.roles WHERE u.email = :email", UserAuth.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public Optional<UserAuth> assignRole(long id, int role) {
        UserAuth userAuth = em.find(UserAuth.class, id);
        UserAuth.Role aux_role = UserAuth.Role.values()[role];
        if (userAuth != null && !userAuth.getRoles().contains(aux_role)) {
            userAuth.getRoles().add(aux_role);
            em.persist(userAuth);
        }
        return Optional.ofNullable(userAuth);
    }

    @Override
    public List<UserAuth.Role> findRoles(long id) {
        return em.createQuery("FROM UserAuth AS u WHERE u.id = :id", UserAuth.class)
                .setParameter("id", id).getSingleResult().getRoles();
    }

    @Override
    public Optional<User> findUserByRoleAndId(UserAuth.Role role, long id) {
        return addEncodedImage(em.createQuery(
                "FROM User u WHERE u.id = :user_id AND EXISTS(FROM UserAuth ua WHERE ua.id = u.id AND :role_id IN elements(ua.roles))"
                , User.class)
                .setParameter("user_id", id).setParameter("role_id", role).
                        getResultList().stream().findFirst());
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
        @SuppressWarnings("unchecked")
        List<JobPost.JobType> resultList = em.createQuery(
                "SELECT DISTINCT jp.jobType FROM JobPost jp WHERE jp.user.id = :id"
        ).setParameter("id", id).getResultList();
        return resultList;
    }

    @Override
    public int findUserRankingInJobType(long id, JobPost.JobType jobType) {

        //TODO: SE PUEDE PASAR A HSQLDB? O ES MUY COMPLICADA?
        String sqlQuery = new StringBuilder()
                .append("SELECT rank FROM (")
                .append("         SELECT user_id, ROW_NUMBER() OVER () AS rank")
                .append("         FROM (SELECT user_id")
                .append("               FROM job_cards")
                .append("               WHERE post_job_type = :jobType")
                .append("               GROUP BY user_id")
                .append("               ORDER BY SUM(post_contract_count) DESC")
                //    ORDERBY esta en una subquery para que funcione correctamente con HSQLDB
                .append("              ) AS users_ordered_by_contract")
                .append("     ) AS rankings")
                .append(" WHERE user_id = :id")
                .toString();

        final Query query = em.createNativeQuery(sqlQuery);
        @SuppressWarnings("unchecked")
        int result = (int) query.setParameter("jobType", jobType.getValue())
                .setParameter("id", id).getResultList().stream().findFirst().orElse(0);
        return result;
    }

    private Optional<User> addEncodedImage(Optional<User> maybeUser) {
        if(maybeUser.isPresent()) {
            ByteImage byteImage = maybeUser.get().getByteImage();
            EncodedImage encodedImage = new EncodedImage(null, null);
            if(byteImage != null)
                encodedImage = new EncodedImage(ImageDataConverter.getEncodedString(byteImage.getData()), byteImage.getType());
            maybeUser.get().setImage(encodedImage);
        }
        return maybeUser;
    }
}
