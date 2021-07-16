package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;

import java.util.List;
import java.util.Optional;

public interface JobCardDao {

    List<JobCard> findAll(int page);

    List<JobCard> findByUserId(long id, int page);

    List<JobCard> search(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes, JobCard.OrderBy orderBy, int page);

    List<JobCard> searchWithCategory(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, JobCard.OrderBy orderBy, int page);

    Optional<JobCard> findByPostId(long id);

    Optional<JobCard> findByPackageIdWithPackageInfoWithInactive(long id);

    Optional<JobCard> findByPostIdWithInactive(long id);

    List<JobCard> findRelatedJobCards(long professional_id, int page);

    int findAllMaxPage();

    int findByUserIdMaxPage(long id);

    int searchMaxPage(String query, JobPost.Zone zone, List<JobPost.JobType> similarTypes);

    int searchWithCategoryMaxPage(String query, JobPost.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes);

    int findRelatedJobCardsMaxPage(long professional_id);
}
