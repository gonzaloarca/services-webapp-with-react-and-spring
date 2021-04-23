package ar.edu.itba.paw.services.nologin;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobPostDao;
import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoLoginJobPostService implements JobPostService {

    @Autowired
    private JobPostDao jobPostDao;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    @Override
    public JobPost create(String email, String title, String availableHours, int jobType, int[] zones) {

        User user = userService.findByEmail(email).orElseThrow(RuntimeException::new);
        userService.assignRole(user.getId(), UserAuth.Role.PROFESSIONAL.ordinal());
        List<JobPost.Zone> parsedZones = Arrays.stream(zones).mapToObj(zone -> JobPost.Zone.values()[zone]).collect(Collectors.toList());
        JobPost.JobType parsedJobType = JobPost.JobType.values()[jobType];
        return jobPostDao.create(user.getId(), title, availableHours, parsedJobType, parsedZones);
    }

    @Override
    public JobPost findById(long id) {
        return jobPostDao.findById(id).orElseThrow(NoSuchElementException::new);
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
    public List<JobPost> search(String query, JobPost.Zone zone, JobPost.JobType jobType) {
        if (jobType == null)
            return jobPostDao.search(query, zone, HirenetUtils.ALL_PAGES);

        return jobPostDao.searchWithCategory(query, zone, jobType, HirenetUtils.ALL_PAGES);
    }


    @Override
    public List<JobPost> search(String query, JobPost.Zone zone, JobPost.JobType jobType, int page) {
        if (jobType == null)
            return jobPostDao.search(query, zone, page);

        return jobPostDao.searchWithCategory(query, zone, jobType, page);
    }

    @Override
    public int findSizeByUserId(long id) {
        return jobPostDao.findSizeByUserId(id);
    }

    @Override
    public int findMaxPage() {
        return jobPostDao.findAllMaxPage();
    }

    @Override
    public int findMaxPageByUserId(long id) {
        return jobPostDao.findMaxPageByUserId(id);
    }

    @Override
    public int findMaxPageSearch(String query, JobPost.Zone value, JobPost.JobType jobType) {
        return jobPostDao.findMaxPageSearch(query, value, jobType);
    }

}
