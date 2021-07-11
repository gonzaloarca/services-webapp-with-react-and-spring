package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.services.JobCardService;
import ar.edu.itba.paw.interfaces.services.JobContractService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
    public int findJobCardsMaxPage() {
        return jobCardService.findAllMaxPage();
    }

    @Override
    public int findReviewsByUserIdMaxPage(long id) {
        return reviewService.findReviewsByProIdMaxPage(id);
    }

    @Override
    public int findJobCardsByUserIdMaxPage(long id) {
        return jobCardService.findByUserIdMaxPage(id);
    }

    @Override
    public int findJobCardsSearchMaxPage(String query, int zone, int jobType, Locale locale) {
        JobPost.Zone parsedZone = JobPost.Zone.values()[zone];
        if (jobType == HirenetUtils.SEARCH_WITHOUT_CATEGORIES)
            return jobCardService.searchMaxPage(query, parsedZone, locale);

        return jobCardService.searchWithCategoryMaxPage(query, parsedZone, JobPost.JobType.values()[jobType], locale);
    }

    @Override
    public int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states) {
        return jobContractService.findContractsByClientIdMaxPage(id, states);
    }

    @Override
    public int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states) {
        return jobContractService.findContractsByProIdMaxPage(id, states);
    }

    @Override
    public int findReviewsByPostIdMaxPage(long id) {
        return reviewService.findByPostIdMaxPage(id);
    }

    @Override
    public int findRelatedJobCardsMaxPage(long professional_id) {
        return jobCardService.findRelatedJobCardsMaxPage(professional_id);
    }
}
