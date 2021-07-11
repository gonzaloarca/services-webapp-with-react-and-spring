package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPost;

import javax.ws.rs.core.UriInfo;
import java.util.List;

public class JobCardDto {
    private JobPostDto jobPost;
    private String title;
    private JobPost.JobType jobType;
    private List<JobPost.Zone> zones;
    private Integer reviewsCount;
    private Double avgRate;
    private Integer contractsCompleted;

    public static JobCardDto fromJobCard(JobCard jobCard, UriInfo uriInfo){
        JobCardDto jobCardDto = new JobCardDto();
        jobCardDto.jobPost = new JobPostDto();
        jobCardDto.jobPost.setId(jobCard.getJobPost().getId());
        jobCardDto.jobPost.setUri(uriInfo.getAbsolutePathBuilder().path("/job-posts/")
                .path(String.valueOf(jobCard.getJobPost().getId())).build());
        //TODO: CHEQUEAR SI SE PUEDE EVITAR HARDCODEAR EL PREFIJO DE LA URI
        jobCardDto.title = jobCard.getJobPost().getTitle();
        jobCardDto.jobType = jobCard.getJobPost().getJobType();
        jobCardDto.zones = jobCard.getJobPost().getZones();
        jobCardDto.reviewsCount = jobCard.getReviewsCount();
        jobCardDto.avgRate = jobCard.getRating();
        jobCardDto.contractsCompleted = jobCard.getContractsCompleted();
        return jobCardDto;
    }

    public JobPostDto getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPostDto jobPost) {
        this.jobPost = jobPost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JobPost.JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobPost.JobType jobType) {
        this.jobType = jobType;
    }

    public List<JobPost.Zone> getZones() {
        return zones;
    }

    public void setZones(List<JobPost.Zone> zones) {
        this.zones = zones;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public Integer getContractsCompleted() {
        return contractsCompleted;
    }

    public void setContractsCompleted(Integer contractsCompleted) {
        this.contractsCompleted = contractsCompleted;
    }
}
