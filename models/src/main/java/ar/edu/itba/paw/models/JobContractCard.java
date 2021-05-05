package ar.edu.itba.paw.models;

public class JobContractCard {
    private final JobContract jobContract;
    private final JobCard jobCard;
    private final Review review;

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
