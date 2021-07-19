package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.AnalyticRanking;

public class AnalyticRankingDto {
    private JobTypeDto jobType;
    private Integer ranking;

    public static AnalyticRankingDto fromAnalyticRanking(AnalyticRanking ranking, String jobTypeMessage){
        AnalyticRankingDto rankingDto = new AnalyticRankingDto();
        rankingDto.jobType = JobTypeDto.fromJobTypeWithLocalizedMessage(ranking.getJobType(), jobTypeMessage);
        rankingDto.ranking = ranking.getRanking();
        return rankingDto;
    }

    public JobTypeDto getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeDto jobType) {
        this.jobType = jobType;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
