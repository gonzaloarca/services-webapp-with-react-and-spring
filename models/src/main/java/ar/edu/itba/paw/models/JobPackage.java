package ar.edu.itba.paw.models;

import java.util.Objects;

public class JobPackage {
    private long id;
    private long postId;
    private String title;
    private String description;
    private Double price;
    private RateType rateType;
    private boolean isActive;

    public JobPackage() {
    }

    public JobPackage(long postId, String title, String description, Double price, RateType rateType) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rateType = rateType;
        this.isActive = true;
    }

    public JobPackage(long id, long postId, String title, String description, Double price, RateType rateType, boolean isActive) {
        this.id = id;
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rateType = rateType;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public boolean is_active() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return "JobPackage{" +
                "id=" + id +
                ", postId=" + postId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rateType=" + rateType +
                ", is_active=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPackage that = (JobPackage) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum RateType {
        HOURLY,
        ONE_TIME,
        TBD
    }
}
