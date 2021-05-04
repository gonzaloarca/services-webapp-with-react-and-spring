package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface JobCardService {

    List<JobCard> findAll();

    List<JobCard> findAll(int page);

    List<JobCard> findByUserId(long id);

    List<JobCard> findByUserId(long id, int page);

    List<JobCard> search(String title, int zone, int jobType, int page);

    JobCard findByPostId(long id);

    JobCard findByPostIdWithInactive(long id);

    List<JobCard> findRelatedJobCards(long professional_id, int page);

    int findSizeByUserId(long id);

    int findMaxPage();

    int findMaxPageByUserId(long id);

    int findMaxPageSearch(String query, JobPost.Zone zone);

    int findMaxPageSearchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType);

    int findMaxPageRelatedJobCards(long professional_id);

    public List<JobPost.JobType> getSimilarTypes(String query);
}
