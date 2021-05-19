package jdbc;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.persistence.jpa.UserDaoJpa;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:db_data_test.sql")
@Transactional
public class UserDaoJDBCTest {

    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            false,
            LocalDateTime.now());

    private static final List<JobPost.JobType> USER1_JOBTYPES = Arrays.asList(JobPost.JobType.values()[1], JobPost.JobType.values()[2], JobPost.JobType.values()[3]);

    private static final int USER1_RANKING_IN_JOBTYPE1 = 1;

    @Autowired
    UserDaoJpa userDaoJDBC;

    @Autowired
    DataSource ds;

    @InjectMocks
    @Autowired
    private UserDaoJpa userDaoJpa;

    private JdbcTemplate jdbcTemplate;

    @Mock
    private UserDaoJpa mockUserDao;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {

        User userTest = new User(
                12,
                "manurodriguez@mail.com",
                "Manuel Rodriguez",
                "11-4536-5656",
                true,
                true,
                LocalDateTime.now());
        String userTestPassword = "password";

        User user = userDaoJpa.register(userTest.getEmail(), userTestPassword, userTest.getUsername(), userTest.getPhone());
        em.flush();

        Assert.assertNotNull(user);
        Assert.assertEquals(userTest, user);
        Assert.assertEquals(userTest.getUsername(), user.getUsername());
        Assert.assertEquals(userTest.getPhone(), user.getPhone());
        Assert.assertEquals(12, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testFindById() {
        Optional<User> user = userDaoJpa.findById(USER1.getId());
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USER1, user.get());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> user = userDaoJpa.findByEmail(USER1.getEmail());

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USER1, user.get());
    }

    @Test
    public void testFindAuthInfo() {
        Optional<UserAuth> userAuth = userDaoJpa.findAuthInfo(USER1.getEmail());

        final UserAuth USERAUTH1 = new UserAuth(USER1.getId(), USER1.getEmail(), false, null);

        Assert.assertTrue(userAuth.isPresent());
        Assert.assertEquals(USERAUTH1, userAuth.get());
    }

    @Test
    public void testUpdateById() {
        String name = "pepe";
        String phone = "123123123";

        Optional<User> maybeUser = userDaoJpa.updateUserById(USER1.getId(), name, phone);
        //FIXME: PORQUE FUNCIONA SIN FLUSHEAR?? ESTA BIEN MOCKEADO?
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(name, maybeUser.get().getUsername());
        Assert.assertEquals(phone, maybeUser.get().getPhone());

        Optional<User> aux = userDaoJpa.findById(USER1.getId());
        Assert.assertTrue(aux.isPresent());
        Assert.assertEquals(name, aux.get().getUsername());
        Assert.assertEquals(phone, aux.get().getPhone());
    }

//    @Test
//    public void testFindUserJobTypes() {
//        List<JobPost.JobType> maybeJobTypes = userDaoJDBC.findUserJobTypes(USER1.getId());
//
//        Assert.assertEquals(USER1_JOBTYPES.size(), maybeJobTypes.size());
//        USER1_JOBTYPES.forEach((jobType -> Assert.assertTrue(maybeJobTypes.contains(jobType))));
//    }
//
//    @Test
//    public void testFindUserRankingInJobType() {
//        int maybeRank = userDaoJDBC.findUserRankingInJobType(USER1.getId(), JobPost.JobType.values()[1]);
//
//        Assert.assertEquals(USER1_RANKING_IN_JOBTYPE1, maybeRank);
//    }
//
//    @Test
//    public void changeUserPasswordTest() {
//        final String NEWPASS = "contrasena";
//
//        userDaoJDBC.changeUserPassword(USER1.getId(), NEWPASS);
//        Optional<UserAuth> userAuth = userDaoJDBC.findAuthInfo(USER1.getEmail());
//
//        Assert.assertTrue(userAuth.isPresent());
//        Assert.assertEquals(NEWPASS, userAuth.get().getPassword());
//    }
//
//    @Test
//    public void verifyUserTest() {
//        userDaoJDBC.verifyUser(USER1.getId());
//        Optional<User> verifiedUser = userDaoJDBC.findById(USER1.getId());
//
//        Assert.assertTrue(verifiedUser.isPresent());
//        Assert.assertTrue(verifiedUser.get().isVerified());
//    }
//
//    @Test
//    public void deleteUserTest() {
//        userDaoJDBC.deleteUser(USER1.getId());
//        Optional<User> deletedUser = userDaoJDBC.findById(USER1.getId());
//
//        Assert.assertTrue(deletedUser.isPresent());
//        Assert.assertFalse(deletedUser.get().isActive());
//    }
}
