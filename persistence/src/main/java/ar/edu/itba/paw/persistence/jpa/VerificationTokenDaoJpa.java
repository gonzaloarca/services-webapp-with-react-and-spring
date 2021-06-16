package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.VerificationTokenDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.TokenNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
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
    public VerificationToken create(User user) {
        if (user == null)
            throw new UserNotFoundException();

        VerificationToken verificationToken = new VerificationToken(UUID.randomUUID().toString(), user, Instant.now());
        em.persist(verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<VerificationToken> findByUserId(long userId) {
        return Optional.ofNullable(em.find(VerificationToken.class, userId));
    }

    @Override
    public void deleteToken(long userId) {
        Optional<VerificationToken> maybeToken = Optional.ofNullable(em.find(VerificationToken.class, userId));

        if (!maybeToken.isPresent())
            throw new TokenNotFoundException();

        em.remove(maybeToken.get());
    }
}
