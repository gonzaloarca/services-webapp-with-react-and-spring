package ar.edu.itba.paw.interfaces.services;

import java.util.List;

public interface PaginationService {

    List<Integer> findCurrentPages(int page, int maxPage);

    int findMaxPagesJobPost();

    int findMaxPagesReviews();

    int findMaxPagesJobPostByUserId(long id);
}
