package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

public class ReviewDto {
    private int rate;
    private String title;
    private String description;
    private JobContractDto jobContract;
    private LocalDateTime creationDate;

    public static ReviewDto fromReview(Review review, UriInfo uriInfo) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.rate = review.getRate();
        reviewDto.title = review.getTitle();
        reviewDto.description = review.getDescription();
        reviewDto.jobContract = new JobContractDto();
        reviewDto.jobContract.setId(review.getJobContract().getId());
        reviewDto.jobContract.setJobContractURI(uriInfo.getBaseUriBuilder().path("/job-contracts/")
                //TODO: CHEQUEAR SI SE PUEDE EVITAR HARDCODEAR EL PREFIJO DE LA URI
                .path(String.valueOf(review.getJobContract().getId())).build());
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public JobContractDto getJobContract() {
        return jobContract;
    }

    public void setJobContract(JobContractDto jobContract) {
        this.jobContract = jobContract;
    }
}
