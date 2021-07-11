package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobContractCard;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class JobContractCardDto {
    private UserDto client;                     //URL e id
    private UserDto professional;               //URL e id

    private JobContractDto jobContractDto;      //URL e id
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

    private JobPostDto jobPost;                 //URL e id
    private JobPackageDto jobPackageDto;        //URL e id

    public static JobContractCardDto fromJobContractCard(JobContractCard card, UriInfo uriInfo, boolean fromClient) {
        JobContractCardDto dto = new JobContractCardDto();
        if (fromClient)
            dto.professional = UserDto.linkDataFromUser(card.getJobContract().getProfessional(), uriInfo);
        else
            dto.client = UserDto.linkDataFromUser(card.getJobContract().getClient(), uriInfo);

        dto.jobContractDto = JobContractDto.linkDataFromJobContract(card.getJobContract(), uriInfo);
        dto.state = JobContractStateDto.fromJobContractState(card.getJobContract().getState());
        dto.creationDate = card.getJobContract().getCreationDate();
        dto.scheduledDate = card.getJobContract().getScheduledDate();

        dto.jobTitle = card.getJobCard().getJobPost().getTitle();
        dto.jobType = JobTypeDto.fromJobType(card.getJobCard().getJobPost().getJobType());
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

        dto.jobPost = JobPostDto.linkDataFromJobPost(card.getJobCard().getJobPost(), uriInfo);
        dto.jobPackageDto = JobPackageDto.linkDataFromJobPackage(card.getJobContract().getJobPackage(), uriInfo);
        return dto;
    }

    public UserDto getClient() {
        return client;
    }

    public void setClient(UserDto client) {
        this.client = client;
    }

    public UserDto getProfessional() {
        return professional;
    }

    public void setProfessional(UserDto professional) {
        this.professional = professional;
    }

    public JobContractDto getJobContractDto() {
        return jobContractDto;
    }

    public void setJobContractDto(JobContractDto jobContractDto) {
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

    public JobPostDto getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPostDto jobPost) {
        this.jobPost = jobPost;
    }

    public JobPackageDto getJobPackageDto() {
        return jobPackageDto;
    }

    public void setJobPackageDto(JobPackageDto jobPackageDto) {
        this.jobPackageDto = jobPackageDto;
    }
}
