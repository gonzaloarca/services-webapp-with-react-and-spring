package jdbc;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jdbc.UserDaoJDBC;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:user_test.sql")
public class UserDaoJDBCTest {

    private static final User USER = new User(
            1,
            "franquesada@mail.com",
            "Francisco Quesada",
            "11-3456-3232",
            true,
            true
    );

    @Autowired
    UserDaoJDBC userDaoJDBC;

    @Autowired
    DataSource ds;

    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testRegister() {

        User userTest = new User(
                3,
                "manurodriguez@mail.com",
                "Manuel Rodriguez",
                "11-4536-5656",
                true,
                true
        );
        String userTestPassword = "password";

        User user = userDaoJDBC.register(userTest.getEmail(),userTestPassword, userTest.getUsername(), userTest.getPhone());

        Assert.assertNotNull(user);
        Assert.assertEquals(userTest, user);
        Assert.assertEquals(userTest.getUsername(), user.getUsername());
        Assert.assertEquals(userTest.getPhone(), user.getPhone());
    }

    @Test
    public void testFindById() {
        Optional<User> user = userDaoJDBC.findById(USER.getId());
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USER, user.get());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> user = userDaoJDBC.findByEmail(USER.getEmail());

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USER, user.get());
    }

}
