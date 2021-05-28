package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobContract;

import java.util.List;
import java.util.Locale;

public interface PaginationService {

    List<Integer> findCurrentPages(int page, int maxPage);

    int findMaxPageJobCards();

    int findMaxPageReviewsByUserId(long id);

    int findMaxPageJobPostsByUserId(long id);

    int findMaxPageJobPostsSearch(String query, int zone, int jobType, Locale locale);

    int findMaxPageContractsByProIdAndStates(long id, List<JobContract.ContractState> states);

    int findMaxPageContractsByClientIdAndStates(long id, List<JobContract.ContractState> states);

    int findMaxPageReviewsByPostId(long id);

    int findMaxPageRelatedJobCards(long professional_id);
}
