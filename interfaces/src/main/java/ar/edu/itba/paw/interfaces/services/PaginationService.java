package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.JobPost;

import java.util.List;

public interface PaginationService {

    List<Integer> findCurrentPages(int page, int maxPage);

    int findMaxPageJobPosts();

    int findMaxPageReviewsByUserId(long id);

    int findMaxPageJobPostsByUserId(long id);

    int findMaxPageJobPostsSearch(String query, JobPost.Zone zone, JobPost.JobType jobType);

    int findMaxPageContractsByUserId(long id);

    int findMaxPageReviewsByPostId(long id);

    int findMaxPageRelatedJobCards(long professional_id);
}
