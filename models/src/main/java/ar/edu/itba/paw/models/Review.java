package ar.edu.itba.paw.models;

import java.util.Objects;

public class Review {
    private int rate;
    private String title;
    private String description;

    public Review() {
    }

    public Review(int rate, String title, String description) {
        this.rate = rate;
        this.title = title;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rate == review.rate && Objects.equals(title, review.title) && Objects.equals(description, review.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, title, description);
    }
}
