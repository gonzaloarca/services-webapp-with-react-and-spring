package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface JobPostDao {

    JobPost create(long userId, String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones);

    Optional<JobPost> findById(long id);

    List<JobPost> findByUserId(long id, int page);

    int findSizeByUserId(long id);

    List<JobPost> findByJobType(JobPost.JobType jobType, int page);

    List<JobPost> findByZone(JobPost.Zone zone, int page);

    List<JobPost> findAll(int page);

    List<JobPost> search(String query, JobPost.Zone zone, int page);

    List<JobPost> searchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, int page);

    boolean updateById(long id, String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones);

    boolean deleteJobPost(long id);
}
