package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoLoginJobPostService implements JobPostService {

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private UserService userService;

    @Override
    public JobPost create(String email, String username, String phone, String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones) {

        //Chequeamos si el user esta registrado
        Optional<User> maybeUser = userService.findByEmail(email);
        User user = null;
        //Si existe vemos si es profesional, si no lo es lo hacemos
        if (maybeUser.isPresent()) {
            if (!maybeUser.get().isProfessional()) {
                //TODO: Es necesario un optional?
//                Optional<User> newRole = userService.switchRole(maybeUser.get().getId());
//                user = newRole.orElse(maybeUser.get());
            } else {
                maybeUser = userService.updateUserByEmail(email, phone, username);
                if (maybeUser.isPresent())
                    user = maybeUser.get();
            }
        } else {
            user = userService.register(email, username, phone, true);
        }
        if (user == null)
            //TODO: LANZAR EXCEPCION APROPIADA
            throw new RuntimeException();

        return jobPostDao.create(user.getId(), title, availableHours, jobType, zones);
    }

    @Override
    public JobPost findById(long id) {
        //TODO excepcion propia
        return jobPostDao.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<JobPost> findByUserId(long id) {
        //TODO excepcion propia
        return jobPostDao.findByUserId(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType) {
        //TODO excepcion propia
        return jobPostDao.findByJobType(jobType).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone) {
        //TODO excepcion propia
        return jobPostDao.findByZone(zone).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<JobPost> findAll() {
        //TODO excepcion propia
        return jobPostDao.findAll().orElseThrow(RuntimeException::new);
    }

    @Override
    public List<JobPost> search(String title, JobPost.Zone zone, JobPost.JobType jobType) {
        //TODO excepcion propia
        if (jobType == null)
            return jobPostDao.search(title, zone).orElseThrow(RuntimeException::new);

        return jobPostDao.searchWithCategory(title, zone, jobType).orElseThrow(RuntimeException::new);
    }

}
