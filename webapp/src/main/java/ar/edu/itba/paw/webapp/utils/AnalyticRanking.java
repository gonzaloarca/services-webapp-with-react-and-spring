package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.models.JobPost;

public class AnalyticRanking {
    private JobPost.JobType jobType;
    private int ranking;

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
