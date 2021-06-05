package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;
import java.util.Locale;

public interface JobCardService {

    List<JobCard> findAll();

    List<JobCard> findAll(int page);

    List<JobCard> findByUserId(long id);

    List<JobCard> findByUserId(long id, int page);

    List<JobCard> search(String title, int zone, int jobType, int page, Locale locale);

    JobCard findByPostId(long id);

    JobCard findByPackageIdWithPackageInfoWithInactive(long id);

    JobCard findByPostIdWithInactive(long id);

    List<JobCard> findRelatedJobCards(long professional_id, int page);

    int findSizeByUserId(long id);

    int findMaxPage();

    int findMaxPageByUserId(long id);

    int findMaxPageSearch(String query, JobPost.Zone zone, Locale locale);

    int findMaxPageSearchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, Locale locale);

    int findMaxPageRelatedJobCards(long professional_id);

    public List<JobPost.JobType> getSimilarTypes(String query, Locale locale);
}
