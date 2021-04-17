package ar.edu.itba.paw.models;

public class JobCard {
    private final JobPost jobPost;
    private final JobPackage.RateType rateType;
    private final Double price;
    private final int contractsCompleted;

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

    public Double getPrice() {
        return price;
    }

    public int getContractsCompleted() {
        return contractsCompleted;
    }

}
