package ar.edu.itba.paw.models;

import java.net.URI;
import java.util.Objects;

public class JobContractCard {
    private final JobContract jobContract;
    private final JobCard jobCard;
    private final Review review;
    private final String scheduledDateStr;
    private final String description;
    private final ByteImage byteImage;

    public JobContractCard(JobContractWithImage jobContract, JobCard jobCard, Review review, String scheduledDateStr) {
        this.jobContract =  new JobContract(jobContract);
        this.jobCard = jobCard;
        this.review = review;
        this.scheduledDateStr = scheduledDateStr;
        this.description = jobContract.description;
        byteImage = jobContract.getByteImage();
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

    public String getScheduledDateStr() {
        return scheduledDateStr;
    }

    public ByteImage getByteImage() {
        return byteImage;
    }

    public String getDescription() {
        return description;
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
