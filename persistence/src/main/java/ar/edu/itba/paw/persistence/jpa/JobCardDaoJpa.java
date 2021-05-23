package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.JobPostZone;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JobCardDaoJpa implements JobCardDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<JobCard> findAll(int page) {
//        Query nativeQuery = em.createNativeQuery(
//                "SELECT post_id FROM job_post WHERE post_is_active = TRUE"
//        );
//        return executePageQuery(page,nativeQuery);
        return new ArrayList<>();
    }

    @Override
    public List<JobCard> findByUserId(long id, int page) {
        return null;
    }

    @Override
    public List<JobCard> search(String query, JobPostZone.Zone zone, List<JobPost.JobType> similarTypes, int page) {
        return null;
    }

    @Override
    public List<JobCard> searchWithCategory(String query, JobPostZone.Zone zone, JobPost.JobType jobType, List<JobPost.JobType> similarTypes, int page) {
        return null;
    }

    @Override
    public Optional<JobCard> findByPostId(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<JobCard> findByPostIdWithInactive(long id) {
        return Optional.empty();
    }

    @Override
    public List<JobCard> findRelatedJobCards(long professional_id, int page) {
        return null;
    }

    @Override
    public int findAllMaxPage() {
        return 0;
    }

    @Override
    public int findMaxPageByUserId(long id) {
        return 0;
    }

    @Override
    public int findMaxPageSearch(String query, JobPostZone.Zone zone) {
        return 0;
    }

    @Override
    public int findMaxPageSearchWithCategory(String query, JobPostZone.Zone zone, JobPost.JobType jobType) {
        return 0;
    }

    @Override
    public int findMaxPageRelatedJobCards(long professional_id) {
        return 0;
    }

//    private List<JobCard> executePageQuery(int page, Query nativeQuery) {
//        if (page != HirenetUtils.ALL_PAGES) {
//            nativeQuery.setFirstResult((page) * HirenetUtils.PAGE_SIZE);
//            nativeQuery.setMaxResults(HirenetUtils.PAGE_SIZE);
//        }
//
//        @SuppressWarnings("unchecked")
//        List<Long> filteredIds = (List<Long>) nativeQuery.getResultList().stream()
//                .map(e -> Long.valueOf(e.toString())).collect(Collectors.toList());
//
//        if (filteredIds.isEmpty())
//            return new ArrayList<>();
//
//        return em.createQuery("SELECT new ar.edu.itba.paw.models.JobCard(post,package.rateType,package.price,postImage,0,0) FROM JobPost post LEFT JOIN JobPackage package ON post = package.jobPost LEFT JOIN User user ON post.user = user LEFT JOIN JobPostImage postImage ON postImage.jobPost = post WHERE post.id in :filteredIds", JobCard.class)
//                .setParameter("filteredIds", filteredIds).getResultList();
//    }
}
