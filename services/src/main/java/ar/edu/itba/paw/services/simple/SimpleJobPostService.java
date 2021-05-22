package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import exceptions.JobPostNotFoundException;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class SimpleJobPostService implements JobPostService {

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private UserService userService;

    @Override
    public JobPost create(String email, String title, String availableHours, int jobType, int[] zones) {
        User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
        userService.assignRole(user.getId(), UserRole.Role.PROFESSIONAL.ordinal());
        List<JobPost.Zone> parsedZones =
                Arrays.stream(zones).mapToObj(zone -> JobPost.Zone.values()[zone]).collect(Collectors.toList());
        JobPost.JobType parsedJobType = JobPost.JobType.values()[jobType];
        return jobPostDao.create(user.getId(), title, availableHours, parsedJobType, parsedZones);
    }

    @Override
    public JobPost findById(long id) {
        return jobPostDao.findById(id).orElseThrow(JobPostNotFoundException::new);
    }

    @Override
    public JobPost findByIdWithInactive(long id) {
        return jobPostDao.findByIdWithInactive(id).orElseThrow(JobPostNotFoundException::new);
    }

    @Override
    public List<JobPost> findByUserId(long id) {
        return jobPostDao.findByUserId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobPost> findByUserId(long id, int page) {
        return jobPostDao.findByUserId(id, page);
    }


    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType) {
        return jobPostDao.findByJobType(jobType, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobPost> findByJobType(JobPost.JobType jobType, int page) {
        return jobPostDao.findByJobType(jobType, page);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone) {
        return jobPostDao.findByZone(zone, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobPost> findByZone(JobPost.Zone zone, int page) {
        return jobPostDao.findByZone(zone, page);
    }

    @Override
    public List<JobPost> findAll() {
        return jobPostDao.findAll(HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobPost> findAll(int page) {
        return jobPostDao.findAll(page);
    }

    @Override
    public int findSizeByUserId(long id) {
        return jobPostDao.findSizeByUserId(id);
    }

    @Override
    public boolean updateJobPost(long id, String title, String availableHours, Integer jobType, int[] zones) {
        List<JobPost.Zone> parsedZones = Arrays.stream(zones).mapToObj(zone -> JobPost.Zone.values()[zone]).collect(Collectors.toList());
        JobPost.JobType parsedJobType = JobPost.JobType.values()[jobType];
        return jobPostDao.updateById(id,title,availableHours,parsedJobType,parsedZones);
    }

    @Override
    public boolean deleteJobPost(long id){
        return jobPostDao.deleteJobPost(id);
    }

}
