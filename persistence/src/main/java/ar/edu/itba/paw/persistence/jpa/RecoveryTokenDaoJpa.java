package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.RecoveryTokenDao;
import ar.edu.itba.paw.models.RecoveryToken;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.TokenNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RecoveryTokenDaoJpa implements RecoveryTokenDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public RecoveryToken create(User user) {
        if (user == null)
            throw new UserNotFoundException();

        RecoveryToken recoveryToken = new RecoveryToken(user, UUID.randomUUID().toString(), Instant.now());
        em.persist(recoveryToken);
        return recoveryToken;
    }

    @Override
    public Optional<RecoveryToken> findByUserId(long userId) {
        return Optional.ofNullable(em.find(RecoveryToken.class, userId));
    }

    @Override
    public void deleteToken(long userId) throws TokenNotFoundException{
        Optional<RecoveryToken> tokenOptional = Optional.ofNullable(em.find(RecoveryToken.class, userId));

        if(!tokenOptional.isPresent())
            throw new TokenNotFoundException();

        em.remove(tokenOptional.get());
    }
}
