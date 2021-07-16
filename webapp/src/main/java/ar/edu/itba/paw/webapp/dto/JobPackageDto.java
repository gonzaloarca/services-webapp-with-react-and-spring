package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPackage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class JobPackageDto {
    private long id;
    private LinkDto jobPost;
    private String title;
    private String description;
    private Double price;
    private JobPackageRateTypeDto rateType;
    private Boolean isActive;

    public static JobPackageDto fromJobPackage(JobPackage jobPackage, UriInfo uriInfo) {
        JobPackageDto jobPackageDto = new JobPackageDto();
        jobPackageDto.id = jobPackage.getId();
        jobPackageDto.jobPost = LinkDto.fromUriAndId(
                uriInfo.getBaseUriBuilder().path("/job-posts/").path(String.valueOf(jobPackage.getPostId())).build(),
                jobPackage.getPostId());
        jobPackageDto.title = jobPackage.getTitle();
        jobPackageDto.description = jobPackage.getDescription();
        jobPackageDto.price = jobPackage.getPrice();
        jobPackageDto.rateType = JobPackageRateTypeDto.fromJobPackageRateType(jobPackage.getRateType());
        jobPackageDto.isActive = jobPackage.is_active();
        return jobPackageDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public JobPackageRateTypeDto getRateType() {
        return rateType;
    }

    public void setRateType(JobPackageRateTypeDto rateType) {
        this.rateType = rateType;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LinkDto getJobPost() {
        return jobPost;
    }

    public void setJobPost(LinkDto jobPost) {
        this.jobPost = jobPost;
    }
}
