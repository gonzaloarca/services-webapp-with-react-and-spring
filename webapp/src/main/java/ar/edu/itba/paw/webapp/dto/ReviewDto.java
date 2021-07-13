package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

public class ReviewDto {
    private int rate;
    private String title;
    private String description;
    private JobContractDto jobContract;
    private UserDto client;
    private LocalDateTime creationDate;

    public static ReviewDto fromReview(Review review, UriInfo uriInfo) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.rate = review.getRate();
        reviewDto.title = review.getTitle();
        reviewDto.description = review.getDescription();
        reviewDto.jobContract = JobContractDto.linkDataFromJobContract(review.getJobContract(), uriInfo);
        reviewDto.client = UserDto.linkDataFromUser(review.getClient(), uriInfo);
        reviewDto.creationDate = review.getCreationDate();
        return reviewDto;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
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

    public JobContractDto getJobContract() {
        return jobContract;
    }

    public void setJobContract(JobContractDto jobContract) {
        this.jobContract = jobContract;
    }

    public UserDto getClient() {
        return client;
    }

    public void setClient(UserDto client) {
        this.client = client;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
