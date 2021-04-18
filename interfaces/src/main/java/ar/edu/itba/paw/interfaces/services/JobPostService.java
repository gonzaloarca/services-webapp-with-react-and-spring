package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface JobPostService {

    JobPost create(String email, String username, String phone, String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones);

    JobPost findById(long id);

    List<JobPost> findByUserId(long id);

    List<JobPost> findByJobType(JobPost.JobType jobType);

    List<JobPost> findByZone(JobPost.Zone zone);

    List<JobPost> findAll();

    List<JobPost> search(String title, JobPost.Zone zone, JobPost.JobType jobType);

}
