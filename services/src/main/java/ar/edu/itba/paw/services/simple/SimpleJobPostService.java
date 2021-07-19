package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
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
    public JobPost create(long proId, String title, String availableHours, int jobType, List<Integer> zones) {
        userService.assignRole(proId, UserAuth.Role.PROFESSIONAL.ordinal());
        List<JobPost.Zone> parsedZones = zones.stream().map(zone -> JobPost.Zone.values()[zone]).collect(Collectors.toList());
        JobPost.JobType parsedJobType = JobPost.JobType.values()[jobType];
        return jobPostDao.create(proId, title, availableHours, parsedJobType, parsedZones);
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
    public List<JobPost> findAll(int page) {
        return jobPostDao.findAll(page);
    }

    @Override
    public int findAllMaxPage() {
        return jobPostDao.findAllMaxPage();
    }

    @Override
    public User findUserByPostId(long id) {
        return jobPostDao.findUserByPostId(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public int findSizeByUserId(long id) {
        return jobPostDao.findSizeByUserId(id);
    }

    @Override
    public boolean updateJobPost(long id, String title, String availableHours, Integer jobType, List<Integer> zones, boolean isActive) {
        List<JobPost.Zone> parsedZones = zones.stream().map(zone -> JobPost.Zone.values()[zone]).collect(Collectors.toList());
        JobPost.JobType parsedJobType = JobPost.JobType.values()[jobType];
        if (!jobPostDao.updateById(id, title, availableHours, parsedJobType, parsedZones, isActive))
            throw new JobPostNotFoundException();
        else return true;
    }

}
