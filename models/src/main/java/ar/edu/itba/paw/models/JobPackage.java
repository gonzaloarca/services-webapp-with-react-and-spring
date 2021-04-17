package ar.edu.itba.paw.models;

import java.util.Objects;

public class JobPackage {
    private final long id;
    private final long postId;
    private final String title;
    private final String description;
    private final Double price;
    private final RateType rateType;
    private final boolean isActive;

    public JobPackage(long id, long postId, String title, String description, Double price, RateType rateType, boolean is_active) {
        this.id = id;
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rateType = rateType;
        this.isActive = is_active;
    }

    public long getId() {
        return id;
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public RateType getRateType() {
        return rateType;
    }

    public boolean is_active() {
        return isActive;
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
        HOURLY("JobPackage.RateType.HOURLY"),
        ONE_TIME("JobPackage.RateType.ONE_TIME"),
        TBD("JobPackage.RateType.TBD");

        private final String stringCode;
        private final int value;

        RateType(final String stringCode) {
            this.stringCode = stringCode;
            this.value = ordinal();
        }

        public String getStringCode() {
            return stringCode;
        }

        public int getValue() {
            return value;
        }

    }
}
