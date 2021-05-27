package jdbc;

import config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//TODO: renombrar a JPA
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:user_data_test.sql")
@Transactional
public class VerificationTokenDaoJpaTest {
    // TODO: Implementar tests

    @Test
    public void testFindByInvalidUserId() {

    }

    @Test
    public void testFindByUnverifiedUserId() {

    }

    @Test
    public void testFindByVerifiedUserId() {

    }

    @Test
    public void testCreate() {

    }

    @Test
    public void testDeleteTokenWithValidUser() {

    }

    @Test
    public void testDeleteTokenWithInvalidUser() {

    }


}
