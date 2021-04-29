package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface JobPostService {

    JobPost create(String email, String title, String availableHours, int jobType, int[] zones);

    JobPost findById(long id);

    List<JobPost> findByUserId(long id);

    List<JobPost> findByUserId(long id, int page);

    List<JobPost> findByJobType(JobPost.JobType jobType);

    List<JobPost> findByJobType(JobPost.JobType jobType, int page);

    List<JobPost> findByZone(JobPost.Zone zone);

    List<JobPost> findByZone(JobPost.Zone zone, int page);

    List<JobPost> findAll();

    List<JobPost> findAll(int page);

    List<JobPost> search(String query, JobPost.Zone zone, JobPost.JobType jobType);

    List<JobPost> search(String query, JobPost.Zone zone, JobPost.JobType jobType, int page);

    int findSizeByUserId(long id);

    int findMaxPage();

    int findMaxPageByUserId(long id);

    int findMaxPageSearch(String query, JobPost.Zone zone, JobPost.JobType jobType);

    boolean updateJobPost(long id, String title, String availableHours, Integer jobType, int[] zones);

    boolean deleteJobPost(long id);
}
