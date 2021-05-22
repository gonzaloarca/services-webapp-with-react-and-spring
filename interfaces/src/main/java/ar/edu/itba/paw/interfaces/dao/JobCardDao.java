package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostZone;

import java.util.List;
import java.util.Optional;

public interface JobCardDao {

    List<JobCard> findAll(int page);

    List<JobCard> findByUserId(long id, int page);

    List<JobCard> search(String query, JobPostZone.Zone zone, List<JobPost.JobType> similarTypes, int page);

    List<JobCard> searchWithCategory(String query, JobPostZone.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, int page);

    Optional<JobCard> findByPostId(long id);

    Optional<JobCard> findByPostIdWithInactive(long id);

    List<JobCard> findRelatedJobCards(long professional_id, int page);

    int findAllMaxPage();

    int findMaxPageByUserId(long id);

    int findMaxPageSearch(String query, JobPostZone.Zone zone);

    int findMaxPageSearchWithCategory(String query, JobPostZone.Zone zone, JobPost.JobType jobType);

    int findMaxPageRelatedJobCards(long professional_id);
}
