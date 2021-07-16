package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface JobPostService {

    JobPost create(long proId, String title, String availableHours, int jobType, List<Integer> zones);

    JobPost findById(long id);

    JobPost findByIdWithInactive(long id);

    List<JobPost> findByUserId(long id);

    List<JobPost> findByUserId(long id, int page);

    List<JobPost> findByJobType(JobPost.JobType jobType);

    List<JobPost> findByJobType(JobPost.JobType jobType, int page);

    List<JobPost> findByZone(JobPost.Zone zone);

    List<JobPost> findByZone(JobPost.Zone zone, int page);

    List<JobPost> findAll();

    List<JobPost> findAll(int page);

    User findUserByPostId(long id);

    int findSizeByUserId(long id);

    boolean updateJobPost(long id, String title, String availableHours, Integer jobType, List<Integer> zones, boolean isActive);
}
