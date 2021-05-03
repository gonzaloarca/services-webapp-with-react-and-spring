package ar.edu.itba.paw.services.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class SimpleJobCardService implements JobCardService {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobCardDao jobCardDao;

    @Override
    public List<JobCard> findAll() {
        return jobCardDao.findAll(HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobCard> findAll(int page) {
        return jobCardDao.findAll(page);
    }

    @Override
    public List<JobCard> findByUserId(long id) {
        return jobCardDao.findByUserId(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobCard> findByUserId(long id,int page) {
        return jobCardDao.findByUserId(id, page);
    }

    @Override
    public List<JobCard> search(String title, JobPost.Zone zone, JobPost.JobType jobType, int page) {
        if (jobType == null)
            return jobCardDao.search(title, zone, page);

        return jobCardDao.searchWithCategory(title, zone, jobType, page);
    }

    @Override
    public List<JobCard> findByUserIdWithReview(long id) {
        return findByUserIdWithReview(id, HirenetUtils.ALL_PAGES);
    }

    @Override
    public List<JobCard> findByUserIdWithReview(long id,int page) {
        return jobCardDao.findByUserIdWithReview(id, page);
    }

    @Override
    public JobCard findByPostId(long id) {
        return jobCardDao.findByPostId(id).orElseThrow(NoSuchElementException::new);
  }

    @Override
    public List<JobCard> findRelatedJobCards(long professional_id, int page) {
        return jobCardDao.findRelatedJobCards(professional_id, page);
    }

    @Override
    public int findSizeByUserId(long id) {
        return jobPostService.findSizeByUserId(id);
    }

    @Override
    public int findMaxPage() {
        return jobCardDao.findAllMaxPage();
    }

    @Override
    public int findMaxPageByUserId(long id) {
        return jobCardDao.findMaxPageByUserId(id);
    }

    @Override
    public int findMaxPageSearch(String query, JobPost.Zone value) {
        return jobCardDao.findMaxPageSearch(query, value);
    }

    @Override
    public int findMaxPageSearchWithCategory(String query, JobPost.Zone value, JobPost.JobType jobType) {
        return jobCardDao.findMaxPageSearchWithCategory(query, value, jobType);
    }

    @Override
    public int findMaxPageRelatedJobCards(long professional_id) {
        return jobCardDao.findMaxPageRelatedJobCards(professional_id);
    }

}
