package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.services.MailingService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.VerificationTokenService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotVerifiedException;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class SimpleUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private MailingService mailingService;

    @Override
    public User register(String email, String password, String username, String phone,
                         ByteImage image, Locale locale) throws UserAlreadyExistsException, UserNotVerifiedException {
        Optional<User> maybeUser = userDao.findByEmail(email);

        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            if (user.isVerified())
                throw new UserAlreadyExistsException();

            //registrar usuario de nuevo
            if (image == null)
                userDao.updateUserById(user.getId(), username, phone);
            else
                userDao.updateUserById(user.getId(), username, phone, image);
            userDao.changeUserPassword(user.getId(), passwordEncoder.encode(password));

            VerificationToken token = verificationTokenService.create(user);
            mailingService.sendVerificationTokenEmail(user, token, locale);

            throw new UserNotVerifiedException();
        }

        User registeredUser;
        if (image == null)
            registeredUser = userDao.register(email, passwordEncoder.encode(password), username, phone);
        else
            registeredUser = userDao.register(email, passwordEncoder.encode(password), username, phone, image);

        VerificationToken token = verificationTokenService.create(registeredUser);
        mailingService.sendVerificationTokenEmail(registeredUser, token, locale);

        return registeredUser;
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User updateUserByEmail(String email, String phone, String name) {
        return userDao.updateUserByEmail(email, phone, name).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User updateUserById(long id, String name, String phone) {
        return userDao.updateUserById(id, name, phone).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User updateUserById(long id, String name, String phone, ByteImage byteImage) {
        return userDao.updateUserById(id, name, phone, byteImage).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User getUserByRoleAndId(int role, long id) {
        return userDao.findUserByRoleAndId(UserAuth.Role.values()[role], id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<UserAuth> getAuthInfo(String email) {
        return userDao.findAuthInfo(email);
    }

    @Override
    public void assignRole(long id, int role) {
        List<UserAuth.Role> roles = userDao.findRoles(id);
        if (!roles.contains(UserAuth.Role.values()[role]))
            userDao.assignRole(id, role);
    }

    @Override
    public boolean validCredentials(String email, String password) {
        UserAuth auth = userDao.findAuthInfo(email).orElseThrow(UserNotFoundException::new);
        return passwordEncoder.matches(password, auth.getPassword());
    }

    @Override
    public void changeUserPassword(String email, String password) {
        User user = userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
        userDao.changeUserPassword(user.getId(), passwordEncoder.encode(password));
    }

    @Override
    public List<JobPost.JobType> findUserJobTypes(long id) {
        return userDao.findUserJobTypes(id);
    }

    @Override
    public int findUserRankingInJobType(long id, JobPost.JobType jobType) {
        return userDao.findUserRankingInJobType(id, jobType);
    }

    @Override
    public List<AnalyticRanking> findUserAnalyticRankings(long id) {
        List<AnalyticRanking> analyticRankings = new ArrayList<>();
        findUserJobTypes(id).forEach((jobType -> analyticRankings.add(new AnalyticRanking(jobType,
                findUserRankingInJobType(id, jobType)))));

        return analyticRankings;
    }

    @Override
    public void recoverUserPassword(String email, Locale locale){
        String pass = generateNewPassword();
        try {
            changeUserPassword(email, pass);
            mailingService.sendRecoverPasswordEmail(email, pass, locale);
        } catch (UserNotFoundException e) {
            //Si el usuario no existe, no hago nada
        }
    }

    private String generateNewPassword() {
        final int PASSWORD_LENGTH = 10;

        ArrayList<Character> charList = new ArrayList<>();

        for(char c = 'a'; c <= 'z'; c++) {
            charList.add(c);
        }
        for(char c = 'A'; c <= 'Z'; c++) {
            charList.add(c);
        }
        for(char c = '0'; c <= '9'; c++) {
            charList.add(c);
        }

        char[] chars = new char[charList.size()];
        for(int i = 0; i < charList.size(); i++)
            chars[i] = charList.get(i);

        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .selectFrom(chars)
                .build();
        return pwdGenerator.generate(PASSWORD_LENGTH);
    }

    @Override
    public UserWithImage findUserWithImage(long id) {
        return userDao.findUserWithImage(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ByteImage findImageByUserId(long id) {
        return userDao.findImageByUserId(id).orElseThrow(ImageNotFoundException::new);
    }
}
