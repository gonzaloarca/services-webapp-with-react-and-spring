package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SimplePaginationService implements PaginationService {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobContractService jobContractService;

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
        return jobPostService.findMaxPage();
    }

    @Override
    public int findMaxPageReviewsByUserId(long id) {
        return reviewService.findMaxPageReviewsByUserId(id);
    }

    @Override
    public int findMaxPageJobPostsByUserId(long id) {
        return jobPostService.findMaxPageByUserId(id);
    }

    @Override
    public int findMaxPageJobPostsSearch(String query, JobPost.Zone zone, JobPost.JobType jobType) {
        return jobPostService.findMaxPageSearch(query, zone, jobType);
    }

    @Override
    public int findMaxPageContractsByUserId(long id) {
        return jobContractService.findMaxPageContractsByUserId(id);
    }

    @Override
    public int findMaxPageReviewsByPostId(long id) {
        return reviewService.findMaxPageByPostId(id);
    }
}
