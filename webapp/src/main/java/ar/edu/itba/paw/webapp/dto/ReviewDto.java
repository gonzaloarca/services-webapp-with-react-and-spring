package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;

import java.time.LocalDateTime;

public class ReviewDto {
    private int rate;
    private String title;
    private String description;
    private JobContractDto jobContractDto;
    private LocalDateTime creationDate;

    public static ReviewDto fromReview(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.rate = review.getRate();
        reviewDto.title = review.getTitle();
        reviewDto.description = review.getDescription();
        reviewDto.jobContractDto = JobContractDto.fromJobContract(review.getJobContract());
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

    public JobContractDto getJobContractDto() {
        return jobContractDto;
    }

    public void setJobContractDto(JobContractDto jobContractDto) {
        this.jobContractDto = jobContractDto;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
