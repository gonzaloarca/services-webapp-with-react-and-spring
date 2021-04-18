package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface JobPostDao {

    JobPost create(long userId, String title, String availableHours, JobPost.JobType jobType, List<JobPost.Zone> zones);

    Optional<JobPost> findById(long id);

    List<JobPost> findByUserId(long id);

    List<JobPost> findByJobType(JobPost.JobType jobType);

    List<JobPost> findByZone(JobPost.Zone zone);

    List<JobPost> findAll();

    List<JobPost> search(String title, JobPost.Zone zone);

    List<JobPost> searchWithCategory(String title, JobPost.Zone zone, JobPost.JobType jobType);

    List<Review> findAllReviews(long id);

    int findJobPostReviewSize(long id);
}
