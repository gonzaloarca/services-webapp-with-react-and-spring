package ar.edu.itba.paw.services.utils;

import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.interfaces.services.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SimplePaginationService implements PaginationService {

    @Autowired
    private JobPostService jobPostService;

    @Override
    public List<Integer> findCurrentPages(int page, int maxPage) {
        if(page < 1)
            throw new IllegalArgumentException();
        int numberOfPages = Math.min(maxPage, 5);
        List<Integer> pages = new ArrayList<>(Collections.nCopies(numberOfPages,-1));
        if(page < 3){
            for (int i = 0; i < numberOfPages; i++) {
                pages.set(i,i+1);
            }
        }else if(maxPage-page <= 3) {
            for (int i = numberOfPages; i > 0; i--) {
                pages.set(numberOfPages-i,maxPage-i + 1);
            }
        }else {
            for (int i = 0; i < numberOfPages; i++) {
                pages.set(i, page - 2 + i);
            }
        }
        return pages;
    }

    @Override
    public int findMaxPagesJobPost() {
        return jobPostService.findMaxPage();
    }

    @Override
    public int findMaxPagesReviews() {
        return 0;
    }

    @Override
    public int findMaxPagesJobPostByUserId(long id) {
        return jobPostService.findMaxPageByUserId(id);
    }
}
