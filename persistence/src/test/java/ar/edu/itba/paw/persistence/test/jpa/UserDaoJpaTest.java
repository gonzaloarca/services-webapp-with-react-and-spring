package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.UserDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:user_data_test.sql")
@Transactional
public class UserDaoJpaTest {

    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "11478956789",
            true,
            false,
            LocalDateTime.now());

    private static final ByteImage USER1_IMAGE = new ByteImage(new byte[]{1, 2, 3, 4, 5, 6},"png");

    private static final List<JobPost.JobType> USER1_JOBTYPES = Arrays.asList(JobPost.JobType.values()[1], JobPost.JobType.values()[2], JobPost.JobType.values()[3]);

    private static final int USER1_RANKING_IN_JOBTYPE1 = 1;

    private static final List<UserAuth.Role> USER1_ROLES = Collections.singletonList(UserAuth.Role.CLIENT);

    private static final byte[] IMAGE_DATA = "image_data_for_testing".getBytes(StandardCharsets.UTF_8);

    private static final String IMAGE_TYPE = "image/jpg";

    private static final long NON_EXISTENT_ID = 999;

    private static final String NON_EXISTENT_EMAIl = "MrX@mail.com";


    @Autowired
    private DataSource ds;

    @Autowired
    @InjectMocks
    private UserDaoJpa userDaoJpa;

    private JdbcTemplate jdbcTemplate;

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
    public void testRegisterWithImage() {
        User userTest = new User(
                12,
                "manurodriguez@mail.com",
                "Manuel Rodriguez",
                "11-4536-5656",
                true,
                true,
                LocalDateTime.now());
        String userTestPassword = "password";

        User user = userDaoJpa.register(userTest.getEmail(), userTestPassword, userTest.getUsername(),
                userTest.getPhone(), new ByteImage(IMAGE_DATA, IMAGE_TYPE));
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
    public void testFindByIdWithNonExistentId() {
        Optional<User> user = userDaoJpa.findById(NON_EXISTENT_ID);
        Assert.assertFalse(user.isPresent());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> user = userDaoJpa.findByEmail(USER1.getEmail());

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USER1, user.get());
    }

    @Test
    public void testFindByEmailWithNonExistentEmail() {
        Optional<User> user = userDaoJpa.findByEmail(NON_EXISTENT_EMAIl);

        Assert.assertFalse(user.isPresent());
    }

    @Test
    public void testFindAuthInfo() {
        Optional<UserAuth> userAuth = userDaoJpa.findAuthInfo(USER1.getEmail());

        final UserAuth USERAUTH1 = new UserAuth(USER1.getId(), USER1.getEmail(), false, null);

        Assert.assertTrue(userAuth.isPresent());
        Assert.assertEquals(USERAUTH1, userAuth.get());
    }

    @Test
    public void testFindAuthInfoWithNonExistentMail() {
        Optional<UserAuth> userAuth = userDaoJpa.findAuthInfo(NON_EXISTENT_EMAIl);

        Assert.assertFalse(userAuth.isPresent());
    }

    @Test
    public void testUpdateById() {
        String name = "pepe";
        String phone = "123123123";

        Optional<UserWithImage> maybeUser = userDaoJpa.updateUserById(USER1.getId(), name, phone);
        em.flush();

        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(name, maybeUser.get().getUsername());
        Assert.assertEquals(phone, maybeUser.get().getPhone());
    }

    @Test
    public void testUpdateByIdWithNonExistentId() {
        String name = "pepe";
        String phone = "123123123";

        Optional<UserWithImage> maybeUser = userDaoJpa.updateUserById(NON_EXISTENT_ID, name, phone);
        em.flush();

        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testUpdateByIdWithImage() {
        String name = "pepe";
        String phone = "123123123";
        byte[] bytes = {1,2,3,4};
        String imageType = "png";
        ByteImage byteImage = new ByteImage(bytes,imageType);
        Optional<User> maybeUser = userDaoJpa.updateUserById(USER1.getId(), name, phone,byteImage);
        em.flush();
        UserWithImage userWithImage = em.find(UserWithImage.class,USER1.getId());

        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertNotNull(userWithImage);
        Assert.assertEquals(name, maybeUser.get().getUsername());
        Assert.assertEquals(phone, maybeUser.get().getPhone());
        Assert.assertEquals(byteImage, userWithImage.getByteImage());
    }

    @Test
    public void  testUpdateUserByEmail(){
        String name = "pepe";
        String phone = "123123123";
        UserDaoJpa mockDaoJpa = Mockito.spy(userDaoJpa);
        Mockito.doReturn(Optional.of(em.find(User.class,USER1.getId()))).when(mockDaoJpa).findByEmail(USER1.getEmail());
        Optional<User> maybeUser = mockDaoJpa.updateUserByEmail(USER1.getEmail(),phone,name);
        em.flush();

        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(name,maybeUser.get().getUsername());
        Assert.assertEquals(phone,maybeUser.get().getPhone());
    }

    @Test
    public void  testUpdateUserByEmailWithNonExistentId(){
        String name = "pepe";
        String phone = "123123123";
        UserDaoJpa mockDaoJpa = Mockito.spy(userDaoJpa);
        Mockito.doReturn(Optional.empty()).when(mockDaoJpa).findByEmail(NON_EXISTENT_EMAIl);
        Optional<User> maybeUser = mockDaoJpa.updateUserByEmail(NON_EXISTENT_EMAIl,phone,name);
        em.flush();

        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void  testUpdateUserByEmailWithNonExistentMail(){
        String name = "pepe";
        String phone = "123123123";

        Optional<User> maybeUser = userDaoJpa.updateUserByEmail(NON_EXISTENT_EMAIl,phone,name);
        em.flush();

        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testAssignRole() {
        List<UserAuth.Role> roles = new java.util.ArrayList<>(USER1_ROLES);
        roles.add(UserAuth.Role.PROFESSIONAL);

        Optional<UserAuth> userAuth = userDaoJpa.assignRole(USER1.getId(), UserAuth.Role.PROFESSIONAL.ordinal());

        Assert.assertTrue(userAuth.isPresent());
        userAuth.get().getRoles().forEach((userRole) ->
                Assert.assertTrue(roles.contains(userRole)));
    }

    @Test
    public void testAssignRoleToNonExistentId() {
        List<UserAuth.Role> roles = new java.util.ArrayList<>(USER1_ROLES);
        roles.add(UserAuth.Role.PROFESSIONAL);

        Optional<UserAuth> userAuth = userDaoJpa.assignRole(NON_EXISTENT_ID, UserAuth.Role.PROFESSIONAL.ordinal());

        Assert.assertFalse(userAuth.isPresent());

    }

    @Test
    public void testChangePassword() {
        final String NEWPASS = "contrasenia";

        boolean changedUserPassword = userDaoJpa.changeUserPassword(USER1.getId(), NEWPASS);
        em.flush();
        UserAuth userAuth = em.find(UserAuth.class,USER1.getId());
        Assert.assertNotNull(userAuth);
        Assert.assertTrue(changedUserPassword);
        Assert.assertEquals(NEWPASS,userAuth.getPassword());
    }

    @Test
    public void testChangePasswordWithNonExistentId() {
        final String NEWPASS = "contrasenia";

        boolean changedUserPassword = userDaoJpa.changeUserPassword(NON_EXISTENT_ID, NEWPASS);
        em.flush();

        Assert.assertFalse(changedUserPassword);
    }

    @Test
    public void testVerifyUser() {

        boolean verified = userDaoJpa.verifyUser(USER1.getId());
        em.flush();
        User user = em.find(User.class,USER1.getId());
        Assert.assertTrue(verified);
        Assert.assertNotNull(user);
        Assert.assertTrue(user.isVerified());
    }

    @Test
    public void testVerifyUserNonExistentId() {

        boolean verified = userDaoJpa.verifyUser(NON_EXISTENT_ID);
        em.flush();
        Assert.assertFalse(verified);
    }

    @Test
    public void testDeleteUser() {

        boolean deleted = userDaoJpa.deleteUser(USER1.getId());
        em.flush();
        User user = em.find(User.class,USER1.getId());
        Assert.assertTrue(deleted);
        Assert.assertNotNull(user);
        Assert.assertFalse(user.isActive());
    }

    @Test
    public void testDeleteUserWithNonExistentId() {

        boolean deleted = userDaoJpa.deleteUser(NON_EXISTENT_ID);
        em.flush();
        Assert.assertFalse(deleted);
    }

    @Test
    public void testFindUserJobTypes() {
        List<JobPost.JobType> maybeJobTypes = userDaoJpa.findUserJobTypes(USER1.getId());

        Assert.assertEquals(USER1_JOBTYPES.size(), maybeJobTypes.size());
        USER1_JOBTYPES.forEach((jobType -> Assert.assertTrue(maybeJobTypes.contains(jobType))));
    }

    @Test
    public void testFindUserJobTypesWithNonExistentId() {
        List<JobPost.JobType> maybeJobTypes = userDaoJpa.findUserJobTypes(NON_EXISTENT_ID);

        Assert.assertTrue( maybeJobTypes.isEmpty());
    }

    @Test
    public void testFindRoles() {
        List<UserAuth.Role> roles = userDaoJpa.findRoles(USER1.getId());
        Assert.assertFalse(roles.isEmpty());
        Assert.assertEquals(USER1_ROLES,roles);
    }

    @Test
    public void testFindRolesWithNonExistentId() {
        List<UserAuth.Role> roles = userDaoJpa.findRoles(NON_EXISTENT_ID);
        Assert.assertTrue(roles.isEmpty());
    }

    @Test
    public void testFindUserByRoleAndId() {
        Optional<User> maybeUser = userDaoJpa.findUserByRoleAndId(USER1_ROLES.get(0),USER1.getId());
        Assert.assertTrue(maybeUser.isPresent());
        Assert.assertEquals(USER1,maybeUser.get());
    }

    @Test
    public void testFindUserByRoleAndIdWithWrongRole() {
        Optional<User> maybeUser = userDaoJpa.findUserByRoleAndId(UserAuth.Role.PROFESSIONAL,USER1.getId());
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testFindUserByRoleAndIdWithNonExistentId() {
        Optional<User> maybeUser = userDaoJpa.findUserByRoleAndId(USER1_ROLES.get(0),NON_EXISTENT_ID);
        Assert.assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testFindUserRankingInJobType() {
        int maybeRank = userDaoJpa.findUserRankingInJobType(USER1.getId(), JobPost.JobType.values()[1]);

        Assert.assertEquals(USER1_RANKING_IN_JOBTYPE1, maybeRank);
    }


    @Test
    public void testFindUserRankingInJobTypeWithNonExistentId() {
        int maybeRank = userDaoJpa.findUserRankingInJobType(NON_EXISTENT_ID, JobPost.JobType.values()[1]);

        Assert.assertEquals(0, maybeRank);
    }

    @Test
    public void testFindUserWithImage(){
        Optional<UserWithImage> userWithImage = userDaoJpa.findUserWithImage(USER1.getId());
        Assert.assertTrue(userWithImage.isPresent());
        Assert.assertNotNull(userWithImage.get().getByteImage());
        Assert.assertEquals(USER1_IMAGE,userWithImage.get().getByteImage());

    }

    @Test
    public void testFindUserWithImageWithNonExistentId(){
        Optional<UserWithImage> userWithImage = userDaoJpa.findUserWithImage(NON_EXISTENT_ID);
        Assert.assertFalse(userWithImage.isPresent());
    }

    @Test
    public void testFindImageByUserId() {
        Optional<ByteImage> maybeImage = userDaoJpa.findImageByUserId(USER1.getId());
        Assert.assertTrue(maybeImage.isPresent());
        Assert.assertNotNull(maybeImage.get());
        Assert.assertEquals(USER1_IMAGE,maybeImage.get());
    }


    @Test
    public void testFindImageByUserIdWithNonExistentId() {
        Optional<ByteImage> maybeImage = userDaoJpa.findImageByUserId(NON_EXISTENT_ID);
        Assert.assertFalse(maybeImage.isPresent());
    }
}
