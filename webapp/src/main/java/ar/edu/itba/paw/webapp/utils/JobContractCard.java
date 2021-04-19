package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.Review;

public class JobContractCard {
    private JobContract jobContract;
    private JobCard jobCard;
    private Review review;

    public JobContractCard(JobContract jobContract, JobCard jobCard, Review review) {
        this.jobContract = jobContract;
        this.jobCard = jobCard;
        this.review = review;
    }

    public JobContract getJobContract() {
        return jobContract;
    }

    public JobCard getJobCard() {
        return jobCard;
    }

    public Review getReview() {
        return review;
    }
}
