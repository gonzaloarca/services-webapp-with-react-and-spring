package ar.edu.itba.paw.models;

import java.util.Objects;

public class JobCard {
    private final JobPost jobPost;
    private final JobPackage.RateType rateType;
    private final Double price;
    private final int contractsCompleted;
    private final int reviewsCount;
    private final JobPostImage postImage;

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, JobPostImage postImage ,int contractsCompleted, int reviewsCount) {
        this.jobPost = jobPost;
        this.rateType = rateType;
        this.price = price;
        this.contractsCompleted = contractsCompleted;
        this.reviewsCount = reviewsCount;
        this.postImage = postImage;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public JobPackage.RateType getRateType() {
        return rateType;
    }

    public Double getPrice() {
        return price;
    }

    public int getContractsCompleted() {
        return contractsCompleted;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public JobPostImage getPostImage() {
        return postImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobCard jobCard = (JobCard) o;
        return jobPost.equals(jobCard.jobPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobPost);
    }
}
