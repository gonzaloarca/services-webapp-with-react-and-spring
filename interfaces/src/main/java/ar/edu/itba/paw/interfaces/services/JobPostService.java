package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPost;

import java.util.List;
import java.util.Optional;

public interface JobPostService {

    JobPost create(String email,String title, String availableHours, int jobType, int[] zones);

    JobPost findById(long id);

    List<JobPost> findByUserId(long id);

    List<JobPost> findByJobType(JobPost.JobType jobType);

    List<JobPost> findByZone(JobPost.Zone zone);

    List<JobPost> findAll();

    List<JobPost> search(String title, JobPost.Zone zone, JobPost.JobType jobType);

    int getJobPostReviewsSize(long id);

}
