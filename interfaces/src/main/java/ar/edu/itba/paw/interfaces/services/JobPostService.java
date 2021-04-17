package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPost;

import java.util.List;
import java.util.Optional;

public interface JobPostService {

    JobPost create(String email,String title, String availableHours, int jobType, int[] zones);

    Optional<JobPost> findById(long id);

    Optional<List<JobPost>> findByUserId(long id);

    Optional<List<JobPost>> findByJobType(JobPost.JobType jobType);

    Optional<List<JobPost>> findByZone(JobPost.Zone zone);

    Optional<List<JobPost>> findAll();

    Optional<List<JobPost>> search(String title, JobPost.Zone zone, JobPost.JobType jobType);

}
