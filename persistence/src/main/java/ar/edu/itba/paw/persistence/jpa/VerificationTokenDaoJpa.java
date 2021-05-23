package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.VerificationTokenDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import exceptions.TokenNotFoundException;
import exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VerificationTokenDaoJpa implements VerificationTokenDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public VerificationToken create(long userId) {
        User user = em.find(User.class, userId);
        if (user == null)
            throw new UserNotFoundException();

        VerificationToken verificationToken = new VerificationToken(UUID.randomUUID().toString(), user, Instant.now());
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> findByUserId(long userId) {
        return em.createQuery("FROM VerificationToken AS vt where vt.user.id = :id", VerificationToken.class).setParameter("id", userId)
                .getResultList().stream().findFirst();
    }

    @Override
    public void deleteToken(long userId) {
        Optional<VerificationToken> maybeToken = em.createQuery("FROM VerificationToken AS vt where vt.user.id = :id", VerificationToken.class)
                .setParameter("id", userId).getResultList().stream().findFirst();

        if (!maybeToken.isPresent())
            throw new TokenNotFoundException();

        em.remove(maybeToken.get());
    }
}
