package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SimplePaginationService implements PaginationService {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobContractService jobContractService;

    @Autowired
    private JobCardService jobCardService;

    @Override
    public List<Integer> findCurrentPages(int page, int maxPage) {
        if (page < 1)
            throw new IllegalArgumentException();
        int numberOfPages = Math.min(maxPage, 5);
        List<Integer> pages = new ArrayList<>(Collections.nCopies(numberOfPages, -1));
        if (page < 3) {
            for (int i = 0; i < numberOfPages; i++) {
                pages.set(i, i + 1);
            }
        } else if (maxPage - page <= 3) {
            for (int i = numberOfPages; i > 0; i--) {
                pages.set(numberOfPages - i, maxPage - i + 1);
            }
        } else {
            for (int i = 0; i < numberOfPages; i++) {
                pages.set(i, page - 2 + i);
            }
        }
        return pages;
    }

    @Override
    public int findMaxPageJobPosts() {
        return jobCardService.findMaxPage();
    }

    @Override
    public int findMaxPageReviewsByUserId(long id) {
        return reviewService.findMaxPageReviewsByUserId(id);
    }

    @Override
    public int findMaxPageJobPostsByUserId(long id) {
        return jobCardService.findMaxPageByUserId(id);
    }

    @Override
    public int findMaxPageJobPostsSearch(String query, JobPost.Zone zone, JobPost.JobType jobType) {
        if (jobType == null)
            return jobCardService.findMaxPageSearch(query, zone);

        return jobCardService.findMaxPageSearchWithCategory(query, zone, jobType);
    }

    @Override
    public int findMaxPageContractsByUserId(long id) {
        return jobContractService.findMaxPageContractsByUserId(id);
    }

    @Override
    public int findMaxPageReviewsByPostId(long id) {
        return reviewService.findMaxPageByPostId(id);
    }

    @Override
    public int findMaxPageRelatedJobPosts(long professional_id) {
        return jobCardService.findMaxPageRelatedJobPosts(professional_id);
    }
}
