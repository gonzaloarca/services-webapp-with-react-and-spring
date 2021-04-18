package ar.edu.itba.paw.models;

import java.util.List;

public class JobCard {
    private final JobPost jobPost;
    private final JobPackage.RateType rateType;
    private final Double price;
    private final int contractsCompleted;
    private final int reviewsCount;
    private final List<JobPostImage> jobPostImages;

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, List<JobPostImage> jobPostImages,int contractsCompleted, int reviewsCount) {
        this.jobPost = jobPost;
        this.rateType = rateType;
        this.price = price;
        this.contractsCompleted = contractsCompleted;
        this.reviewsCount = reviewsCount;
        this.jobPostImages = jobPostImages;
    }

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, List<JobPostImage> jobPostImages,int contractsCompleted) {
        this(jobPost,rateType,price,jobPostImages,contractsCompleted,0);
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

    public List<JobPostImage> getJobPostImages() {
        return jobPostImages;
    }
}
