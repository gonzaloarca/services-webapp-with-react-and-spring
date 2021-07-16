package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.JobPackage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class JobContractCardDto {
    private URI client;
    private URI professional;

    private URI jobContractDto;
    private JobContractStateDto state;
    private LocalDateTime creationDate;
    private LocalDateTime scheduledDate;

    private String jobTitle;
    private JobTypeDto jobType;
    private Integer reviewsCount;
    private Double avgRate;
    private Integer contractsCompleted;
    private String packageTitle;
    private Double price;
    private JobPackageRateTypeDto rateType;
    private URI imageUrl;

    private URI jobPost;
    private URI jobPackageDto;

    public static JobContractCardDto fromJobContractCardWithLocalizedMessage(JobContractCard card, UriInfo uriInfo, String message) {
        JobContractCardDto dto = new JobContractCardDto();
        dto.professional = uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(card.getJobContract().getProfessional().getId())).build();
        dto.client = uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(card.getJobContract().getClient().getId())).build();

        dto.jobContractDto = uriInfo.getBaseUriBuilder()
                        .path("/contracts").path(String.valueOf(card.getJobContract().getId())).build();

        dto.state = JobContractStateDto.fromJobContractState(card.getJobContract().getState());
        dto.creationDate = card.getJobContract().getCreationDate();
        dto.scheduledDate = card.getJobContract().getScheduledDate();

        dto.jobTitle = card.getJobCard().getJobPost().getTitle();
        dto.jobType = JobTypeDto.fromJobTypeWithLocalizedMessage(card.getJobCard().getJobPost().getJobType(), message);
        dto.reviewsCount = card.getJobCard().getReviewsCount();
        dto.avgRate = card.getJobCard().getRating();
        dto.contractsCompleted = card.getJobCard().getContractsCompleted();
        dto.packageTitle = card.getJobContract().getJobPackage().getTitle();
        dto.price = card.getJobContract().getJobPackage().getPrice();
        dto.rateType = JobPackageRateTypeDto.fromJobPackageRateType(card.getJobContract().getJobPackage().getRateType());
        dto.imageUrl = uriInfo.getBaseUriBuilder().path("/job-posts")
                .path(String.valueOf(card.getJobCard().getJobPost().getId()))
                .path("/images")
                .path(String.valueOf(card.getJobCard().getPostImageId()))
                .build();
        long jobPostId = card.getJobCard().getJobPost().getId();
        dto.jobPost = uriInfo.getBaseUriBuilder().path("/job-posts/")
                .path(String.valueOf(jobPostId)).build();
        JobPackage pack = card.getJobContract().getJobPackage();
        dto.jobPackageDto = uriInfo.getBaseUriBuilder()
                .path("/job-posts/")
                .path(String.valueOf(pack.getJobPost().getId()))
                .path("/packages")
                .path(String.valueOf(pack.getId())).build();
        return dto;
    }

    public URI getClient() {
        return client;
    }

    public void setClient(URI client) {
        this.client = client;
    }

    public URI getProfessional() {
        return professional;
    }

    public void setProfessional(URI professional) {
        this.professional = professional;
    }

    public URI getJobContractDto() {
        return jobContractDto;
    }

    public void setJobContractDto(URI jobContractDto) {
        this.jobContractDto = jobContractDto;
    }

    public JobContractStateDto getState() {
        return state;
    }

    public void setState(JobContractStateDto state) {
        this.state = state;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public JobTypeDto getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeDto jobType) {
        this.jobType = jobType;
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

    public String getPackageTitle() {
        return packageTitle;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
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

    public URI getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(URI imageUrl) {
        this.imageUrl = imageUrl;
    }

    public URI getJobPost() {
        return jobPost;
    }

    public void setJobPost(URI jobPost) {
        this.jobPost = jobPost;
    }

    public URI getJobPackageDto() {
        return jobPackageDto;
    }

    public void setJobPackageDto(URI jobPackageDto) {
        this.jobPackageDto = jobPackageDto;
    }
}
