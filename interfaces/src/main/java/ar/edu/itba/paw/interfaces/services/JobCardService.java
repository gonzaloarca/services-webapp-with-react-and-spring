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

    List<JobCard> search(String query, Integer zone, Integer jobType, Integer orderBy, int page, Locale locale);

    JobCard findByPostId(long id);

    JobCard findByPackageIdWithPackageInfoWithInactive(long id);

    JobCard findByPostIdWithInactive(long id);

    List<JobCard> findRelatedJobCards(long professional_id, int page);

    int findByUserIdSize(long id);

    int findAllMaxPage();

    int findByUserIdMaxPage(long id);

    int searchMaxPage(String query, Integer zone, Integer jobType, Locale locale) ;

    int findRelatedJobCardsMaxPage(long professional_id);

    public List<JobPost.JobType> getSimilarTypes(String query, Locale locale);

}
