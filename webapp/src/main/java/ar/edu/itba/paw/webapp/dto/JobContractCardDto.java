package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.JobPackage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class JobContractCardDto {
    private LinkDto client;
    private LinkDto professional;

    private LinkDto jobContractDto;
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

    private LinkDto jobPost;
    private LinkDto jobPackageDto;

    public static JobContractCardDto fromJobContractCardWithLocalizedMessage(JobContractCard card, UriInfo uriInfo, String message) {
        JobContractCardDto dto = new JobContractCardDto();
        long professionalId = card.getJobContract().getProfessional().getId();
        dto.professional = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(professionalId)).build(), professionalId);
        long clientId = card.getJobContract().getClient().getId();
        dto.client = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(clientId)).build(), clientId);

        dto.jobContractDto = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder()
                        .path("/job-posts").path(String.valueOf(card.getJobContract().getJobPackage().getJobPost().getId()))
                        .path("/packages").path(String.valueOf(card.getJobContract().getJobPackage().getId()))
                        .path("/contracts").path(String.valueOf(card.getJobContract().getId())).build(),
                card.getJobContract().getId());

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
        dto.jobPost = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder().path("/job-posts/")
                .path(String.valueOf(jobPostId)).build(), jobPostId);
        JobPackage pack = card.getJobContract().getJobPackage();
        dto.jobPackageDto = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder()
                .path("/job-posts/")
                .path(String.valueOf(pack.getJobPost().getId()))
                .path("/packages")
                .path(String.valueOf(pack.getId())).build(), pack.getId());
        return dto;
    }

    public LinkDto getClient() {
        return client;
    }

    public void setClient(LinkDto client) {
        this.client = client;
    }

    public LinkDto getProfessional() {
        return professional;
    }

    public void setProfessional(LinkDto professional) {
        this.professional = professional;
    }

    public LinkDto getJobContractDto() {
        return jobContractDto;
    }

    public void setJobContractDto(LinkDto jobContractDto) {
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

    public LinkDto getJobPost() {
        return jobPost;
    }

    public void setJobPost(LinkDto jobPost) {
        this.jobPost = jobPost;
    }

    public LinkDto getJobPackageDto() {
        return jobPackageDto;
    }

    public void setJobPackageDto(LinkDto jobPackageDto) {
        this.jobPackageDto = jobPackageDto;
    }
}
