package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;

public class JobCard {
    private JobPost jobPost;
    private JobPackage.RateType rateType;
    private Double price;
    private int contractsCompleted;

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, int contractsCompleted) {
        this.jobPost = jobPost;
        this.rateType = rateType;
        this.price = price;
        this.contractsCompleted = contractsCompleted;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public JobPackage.RateType getRateType() {
        return rateType;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public int getContractsCompleted() {
        return contractsCompleted;
    }
}
