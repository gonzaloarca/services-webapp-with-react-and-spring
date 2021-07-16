package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobContract;

import java.util.List;
import java.util.Locale;

public interface PaginationService {

    List<Integer> findCurrentPages(int page, int maxPage);

    int findJobCardsMaxPage();

    int findReviewsByUserIdMaxPage(long id);

    int findJobCardsByUserIdMaxPage(long id);

    int findJobCardsSearchMaxPage(String query, int zone, int jobType, Locale locale);

    int findContractsByProIdMaxPage(long id, List<JobContract.ContractState> states);

    int findContractsByClientIdMaxPage(long id, List<JobContract.ContractState> states);

    int findReviewsByPostIdMaxPage(long id);

    int findRelatedJobCardsMaxPage(long professional_id);

    int findJobPackageByPostIdMaxPage(long id);
}
