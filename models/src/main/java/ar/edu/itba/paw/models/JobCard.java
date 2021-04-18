package ar.edu.itba.paw.models;

import java.util.List;

public class JobCard {
    private JobPost jobPost;
    private JobPackage.RateType rateType;
    private Double price;
    private int contractsCompleted;
    private List<JobPostImage> jobPostImages;

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, int contractsCompleted, List<JobPostImage> jobPostImages) {
        this.jobPost = jobPost;
        this.rateType = rateType;
        this.price = price;
        this.contractsCompleted = contractsCompleted;
        this.jobPostImages = jobPostImages;
    }

    public JobCard() {
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public JobPackage.RateType getRateType() {
        return rateType;
    }

    public void setRateType(JobPackage.RateType rateType) {
        this.rateType = rateType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getContractsCompleted() {
        return contractsCompleted;
    }

    public void setContractsCompleted(int contractsCompleted) {
        this.contractsCompleted = contractsCompleted;
    }

    public List<JobPostImage> getJobPostImages() {
        return jobPostImages;
    }
}
