package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface JobCardService {

    List<JobCard> findAll();

    List<JobCard> findByUserId(long id);

    List<JobCard> search(String title, JobPost.Zone zone, JobPost.JobType jobType);

    List<JobCard> findByUserIdWithReview(long id);
}
