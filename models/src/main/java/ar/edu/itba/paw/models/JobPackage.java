package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "job_package")
public class JobPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_package_package_id_seq")
    @SequenceGenerator(sequenceName = "job_package_package_id_seq", name = "job_package_package_id_seq", allocationSize = 1)
    @Column(name = "package_id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "job_package_post_id_fkey"))
    private JobPost jobPost;

    @Column(length = 100, nullable = false, name = "package_title")
    private String title;

    @Column(length = 100, nullable = false, name = "package_description")
    private String description;

    @Column(name = "package_price")
    private Double price;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "package_rate_type", nullable = false)
    private RateType rateType;

    @Column(name = "package_is_active", nullable = false)
    private boolean isActive;

    /* default */JobPackage() {
    }

    public JobPackage(JobPost jobPost, String title, String description, Double price, RateType rateType) {
        this.jobPost = jobPost;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rateType = rateType;
        this.isActive = true;
    }

    public JobPackage(long id, JobPost jobPost, String title, String description, Double price, RateType rateType, boolean is_active) {
        this.id = id;
        this.jobPost = jobPost;
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
        return jobPost.getId();
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

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    @Override
    public String toString() {
        return "JobPackage{" +
                "id=" + id +
                ", jobPost=" + jobPost +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rateType=" + rateType +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPackage that = (JobPackage) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum RateType {
        HOURLY("HOURLY"),
        ONE_TIME("ONE_TIME"),
        TBD("TBD");

        private final String description;
        private final long id;

        RateType(final String description) {
            this.description = description;
            this.id = ordinal();
        }

        public String getDescription() {
            return description;
        }

        public long getId() {
            return id;
        }

    }
}
