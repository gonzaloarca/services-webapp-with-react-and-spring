package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.dao.JobPostImageDao;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostImage;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoLoginJobPostService implements JobPostService {

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private JobPostImageDao jobPostImageDao;

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
                Optional<User> newRole = userService.switchRole(maybeUser.get().getId());
                user = newRole.orElse(maybeUser.get());
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
        JobPost jobPost = jobPostDao.findById(id).orElseThrow(RuntimeException::new);
        addImagesToPost(jobPost);
        return jobPost;
    }

    @Override
    public List<JobPost> findByUserId(long id) {
        //TODO excepcion propia
        List<JobPost> jobPosts = jobPostDao.findByUserId(id).orElseThrow(RuntimeException::new);
        jobPosts.forEach(this::addImagesToPost);
        return jobPosts;
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType) {
        List<JobPost> jobPosts = jobPostDao.findByJobType(jobType).orElseThrow(RuntimeException::new);
        jobPosts.forEach(this::addImagesToPost);
        return jobPosts;
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone) {
        List<JobPost> jobPosts = jobPostDao.findByZone(zone).orElseThrow(RuntimeException::new);
        jobPosts.forEach(this::addImagesToPost);
        return jobPosts;
    }

    @Override
    public List<JobPost> findAll() {
        //TODO excepcion propia
        List<JobPost> jobPosts = jobPostDao.findAll().orElseThrow(RuntimeException::new);
        jobPosts.forEach(this::addImagesToPost);
        return jobPosts;
    }

    @Override
    public List<JobPost> search(String title, JobPost.Zone zone, JobPost.JobType jobType) {
        List<JobPost> jobPosts;

        //TODO excepcion propia
        if (jobType == null)
            jobPosts = jobPostDao.search(title, zone).orElseThrow(RuntimeException::new);
        else
            jobPosts = jobPostDao.searchWithCategory(title, zone, jobType).orElseThrow(RuntimeException::new);

        jobPosts.forEach(this::addImagesToPost);
        return jobPosts;
    }

    private void addImagesToPost(JobPost jobPost) {
        //TODO excepcion propia
        jobPost.getImages().addAll(jobPostImageDao.findByPostId(jobPost.getId()).orElseThrow(RuntimeException::new));
    }
}
