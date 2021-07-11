package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPackage;

import javax.ws.rs.core.UriInfo;

public class JobPackageDto {
    private long id;
    private JobPostDto jobPost;
    private String title;
    private String description;
    private Double price;
    private JobPackage.RateType rateType;
    private boolean isActive;

    public static JobPackageDto fromJobPackage(JobPackage jobPackage, UriInfo uriInfo) {
        JobPackageDto jobPackageDto = new JobPackageDto();
        jobPackageDto.id = jobPackage.getId();
        jobPackageDto.jobPost = new JobPostDto();
        jobPackageDto.jobPost.setId(jobPackage.getPostId());
        jobPackageDto.jobPost.setUri(uriInfo.getAbsolutePathBuilder().path("/job-posts/")
                .path(String.valueOf(jobPackage.getPostId())).build());
        //TODO: CHEQUEAR SI SE PUEDE EVITAR HARDCODEAR EL PREFIJO DE LA URI
        jobPackageDto.title = jobPackage.getTitle();
        jobPackageDto.description = jobPackage.getDescription();
        jobPackageDto.price = jobPackage.getPrice();
        jobPackageDto.rateType = jobPackage.getRateType();
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

    public JobPackage.RateType getRateType() {
        return rateType;
    }

    public void setRateType(JobPackage.RateType rateType) {
        this.rateType = rateType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public JobPostDto getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPostDto jobPost) {
        this.jobPost = jobPost;
    }
}
