package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

public class ReviewDto {
    private int rate;
    private String title;
    private String description;
    private LinkDto jobContract;
    private LinkDto client;
    private LocalDateTime creationDate;

    public static ReviewDto fromReview(Review review, UriInfo uriInfo) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.rate = review.getRate();
        reviewDto.title = review.getTitle();
        reviewDto.description = review.getDescription();

        reviewDto.jobContract = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder()
                .path("/job-posts").path(String.valueOf(review.getJobContract().getJobPackage().getJobPost().getId()))
                .path("/packages").path(String.valueOf(review.getJobContract().getJobPackage().getId()))
                .path("/contracts").path(String.valueOf(review.getJobContract().getId())).build(),
                review.getJobContract().getId());

        long clientId = review.getClient().getId();
        reviewDto.client = LinkDto.fromUriAndId(uriInfo.getBaseUriBuilder().path("/users")
                .path(String.valueOf(clientId)).build(), clientId);
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

    public LinkDto getJobContract() {
        return jobContract;
    }

    public void setJobContract(LinkDto jobContract) {
        this.jobContract = jobContract;
    }

    public LinkDto getClient() {
        return client;
    }

    public void setClient(LinkDto client) {
        this.client = client;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
