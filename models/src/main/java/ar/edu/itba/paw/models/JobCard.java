package ar.edu.itba.paw.models;

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
}
