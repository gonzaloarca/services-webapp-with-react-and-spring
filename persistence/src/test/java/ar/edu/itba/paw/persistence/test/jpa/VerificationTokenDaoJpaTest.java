package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.TokenNotFoundException;
import ar.edu.itba.paw.persistence.jpa.VerificationTokenDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:user_data_test.sql")
@Transactional
public class VerificationTokenDaoJpaTest {
    private static final User PROFESSIONAL = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());

    private static final User CLIENT = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true,
            LocalDateTime.now());

    private static final String TOKEN = "ABCDEFGHIJK";

    @PersistenceContext
    private EntityManager em;

    @InjectMocks
    @Autowired
    private VerificationTokenDaoJpa verificationTokenDaoJpa;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource ds;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByUnverifiedUserId() {
        Optional<VerificationToken> tokenOptional = verificationTokenDaoJpa.findByUserId(CLIENT.getId());

        Assert.assertTrue(tokenOptional.isPresent());
        Assert.assertEquals(CLIENT, tokenOptional.get().getUser());
        Assert.assertNotNull(tokenOptional.get().getToken());
        Assert.assertEquals(TOKEN, tokenOptional.get().getToken());
        Assert.assertNotNull(tokenOptional.get().getCreationDate());
    }

    @Test
    public void testFindByVerifiedUserId() {
        Optional<VerificationToken> tokenOptional = verificationTokenDaoJpa.findByUserId(PROFESSIONAL.getId());

        Assert.assertFalse(tokenOptional.isPresent());
    }

    @Test
    public void testCreate() {
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"verification_token");
        VerificationToken verificationToken = verificationTokenDaoJpa.create(PROFESSIONAL);
        em.flush();

        Assert.assertEquals(PROFESSIONAL, verificationToken.getUser());
        Assert.assertNotNull(verificationToken.getToken());
        Assert.assertNotNull(verificationToken.getCreationDate());

        Assert.assertEquals(rowCount + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate,"verification_token"));
    }

    @Test
    public void testDeleteTokenWithValidUser() {
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"verification_token");
        verificationTokenDaoJpa.deleteToken(CLIENT.getId());
        em.flush();

        Assert.assertEquals(rowCount - 1, JdbcTestUtils.countRowsInTable(jdbcTemplate,"verification_token"));
    }

    @Test(expected = TokenNotFoundException.class)
    public void testDeleteTokenWithInvalidUser() {
        verificationTokenDaoJpa.deleteToken(PROFESSIONAL.getId());
    }

}
