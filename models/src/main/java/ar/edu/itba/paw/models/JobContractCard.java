package ar.edu.itba.paw.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobContractCard)) return false;
        JobContractCard that = (JobContractCard) o;
        return Objects.equals(jobContract, that.jobContract) && Objects.equals(jobCard, that.jobCard) && Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobContract, jobCard, review);
    }
}
