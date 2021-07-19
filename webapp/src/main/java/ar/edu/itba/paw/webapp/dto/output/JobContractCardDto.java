package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.JobContractCard;
import ar.edu.itba.paw.models.JobPackage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class JobContractCardDto {
    private URI client;
    private URI professional;
    private Long id;
    private URI jobContract;
    private JobContractStateDto state;
    private String description;
    private URI contractImage;
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
    private URI postImage;
    private URI jobPost;
    private URI jobPackage;
    private Boolean wasRescheduled;
    private Integer rate;

    public static JobContractCardDto fromJobContractCardWithLocalizedMessage(JobContractCard card, UriInfo uriInfo, String message) {
        JobContractCardDto dto = new JobContractCardDto();
        dto.professional = uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(card.getJobContract().getProfessional().getId())).build();
        dto.client = uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(card.getJobContract().getClient().getId())).build();

        dto.jobContract = uriInfo.getBaseUriBuilder()
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
        dto.postImage = card.getJobCard().getPostImageId() != null? uriInfo.getBaseUriBuilder().path("/job-posts")
                .path(String.valueOf(card.getJobCard().getJobPost().getId()))
                .path("/images")
                .path(String.valueOf(card.getJobCard().getPostImageId()))
                .build() : null;
        long jobPostId = card.getJobCard().getJobPost().getId();
        dto.jobPost = uriInfo.getBaseUriBuilder().path("/job-posts/")
                .path(String.valueOf(jobPostId)).build();
        JobPackage pack = card.getJobContract().getJobPackage();
        dto.jobPackage = uriInfo.getBaseUriBuilder()
                .path("/job-posts/")
                .path(String.valueOf(pack.getJobPost().getId()))
                .path("/packages")
                .path(String.valueOf(pack.getId())).build();
        dto.description = card.getDescription();
        dto.wasRescheduled = card.getWasRescheduled();
        dto.id = card.getJobContract().getId();
        dto.rate = card.getReview().getRate();
        dto.contractImage = card.getByteImage() != null ? uriInfo.getBaseUriBuilder()
                .path("/contracts").path(String.valueOf(card.getJobContract().getId())).path("/image").build() : null;;
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

    public URI getJobPost() {
        return jobPost;
    }

    public void setJobPost(URI jobPost) {
        this.jobPost = jobPost;
    }

    public URI getJobContract() {
        return jobContract;
    }

    public void setJobContract(URI jobContract) {
        this.jobContract = jobContract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URI getContractImage() {
        return contractImage;
    }

    public void setContractImage(URI contractImage) {
        this.contractImage = contractImage;
    }

    public URI getPostImage() {
        return postImage;
    }

    public void setPostImage(URI postImage) {
        this.postImage = postImage;
    }

    public URI getJobPackage() {
        return jobPackage;
    }

    public void setJobPackage(URI jobPackage) {
        this.jobPackage = jobPackage;
    }

    public Boolean getWasRescheduled() {
        return wasRescheduled;
    }

    public void setWasRescheduled(Boolean wasRescheduled) {
        this.wasRescheduled = wasRescheduled;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
