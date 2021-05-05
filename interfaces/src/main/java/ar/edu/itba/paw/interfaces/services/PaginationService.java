package ar.edu.itba.paw.interfaces.services;

import java.util.List;

public interface PaginationService {

    List<Integer> findCurrentPages(int page, int maxPage);

    int findMaxPageJobCards();

    int findMaxPageReviewsByUserId(long id);

    int findMaxPageJobPostsByUserId(long id);

    int findMaxPageJobPostsSearch(String query, int zone, int jobType);

    int findMaxPageContractsByClientId(long id);

    int findMaxPageContractsByProId(long id);

    int findMaxPageReviewsByPostId(long id);

    int findMaxPageRelatedJobCards(long professional_id);
}
