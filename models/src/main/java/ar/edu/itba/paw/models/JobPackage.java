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

    @Column(name = "post_id", nullable = false, insertable = false, updatable = false)
    private long postId;

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

    /* default */JobPackage(){

    }

    public JobPackage(long postId, String title, String description, Double price, RateType rateType) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rateType = rateType;
        this.isActive = true;
    }

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

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPostId(long postId) {
        this.postId = postId;
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
