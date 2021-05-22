package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostZone;

import java.util.List;
import java.util.Optional;

public interface JobPostDao {

    JobPost create(long userId, String title, String availableHours, JobPost.JobType jobType, List<JobPostZone> zones);

    Optional<JobPost> findById(long id);

    Optional<JobPost> findByIdWithInactive(long id);

    List<JobPost> findByUserId(long id, int page);

    int findSizeByUserId(long id);

    List<JobPost> findByJobType(JobPost.JobType jobType, int page);

    List<JobPost> findByZone(JobPostZone.Zone zone, int page);

    List<JobPost> findAll(int page);

    boolean updateById(long id, String title, String availableHours, JobPost.JobType jobType, List<JobPostZone> zones);

    boolean deleteJobPost(long id);
}
