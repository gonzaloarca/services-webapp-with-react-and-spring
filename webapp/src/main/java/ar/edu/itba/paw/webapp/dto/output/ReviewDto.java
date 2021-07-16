package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Review;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class ReviewDto {
    private int rate;
    private String title;
    private String description;
    private URI jobContract;
    private URI client;
    private LocalDateTime creationDate;

    public static ReviewDto fromReview(Review review, UriInfo uriInfo) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.rate = review.getRate();
        reviewDto.title = review.getTitle();
        reviewDto.description = review.getDescription();

        reviewDto.jobContract = uriInfo.getBaseUriBuilder()
                .path("/contracts").path(String.valueOf(review.getJobContract().getId())).build();

        long clientId = review.getClient().getId();
        reviewDto.client = uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(clientId)).build();
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

    public URI getJobContract() {
        return jobContract;
    }

    public void setJobContract(URI jobContract) {
        this.jobContract = jobContract;
    }

    public URI getClient() {
        return client;
    }

    public void setClient(URI client) {
        this.client = client;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
