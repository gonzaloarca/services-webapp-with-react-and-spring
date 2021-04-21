package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface JobCardService {

    List<JobCard> findAll();

    List<JobCard> findAll(int page);

    List<JobCard> findByUserId(long id);

    List<JobCard> findByUserId(long id,int page);

    List<JobCard> search(String title, JobPost.Zone zone, JobPost.JobType jobType);

    List<JobCard> search(String title, JobPost.Zone zone, JobPost.JobType jobType,int page);

    List<JobCard> findByUserIdWithReview(long id);

    List<JobCard> findByUserIdWithReview(long id,int page);


    JobCard findByPostId(long id);
}
