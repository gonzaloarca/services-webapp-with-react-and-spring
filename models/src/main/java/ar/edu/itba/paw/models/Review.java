package ar.edu.itba.paw.models;

import java.util.Objects;

public class Review {
    private int rate;
    private String title;
    private String description;
    private User client;
    private JobPost jobPost;

    public Review() {
    }

    public Review(int rate, String title, String description, User client, JobPost jobPost) {
        this.rate = rate;
        this.title = title;
        this.description = description;
        this.client = client;
        this.jobPost = jobPost;
    }

    public int getRate() {
        return rate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getClient() {
        return client;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rate == review.rate && Objects.equals(title, review.title) && Objects.equals(description, review.description) && Objects.equals(client, review.client) && Objects.equals(jobPost, review.jobPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, title, description, client, jobPost);
    }
}
