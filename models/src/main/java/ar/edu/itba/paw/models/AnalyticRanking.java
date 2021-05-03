package ar.edu.itba.paw.models;

public class AnalyticRanking {
    private final JobPost.JobType jobType;
    private final int ranking;

    public AnalyticRanking(JobPost.JobType jobType, int ranking) {
        this.jobType = jobType;
        this.ranking = ranking;
    }

    public JobPost.JobType getJobType() {
        return jobType;
    }

    public int getRanking() {
        return ranking;
    }
}
