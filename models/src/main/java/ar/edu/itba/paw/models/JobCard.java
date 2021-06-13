package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT job_post.post_id,\n" +
        "       post_title,\n" +
        "       post_is_active,\n" +
        "       post_job_type,\n" +
        "       post_creation_date,\n" +
        "       user_id,\n" +
        "       coalesce(avg(review_rate), 0)        AS rating,\n" +
        "       count(distinct contract.contract_id) AS post_contract_count,\n" +
        "       count(DISTINCT review.contract_id)   as reviews,\n" +
        "       package_price          AS min_pack_price,\n" +
        "       min(package_rate_type) AS min_rate_type,\n" +
        "       pi.image_id            AS card_image_id\n" +
        "FROM   job_post\n" +
        "         NATURAL JOIN job_package pack\n" +
        "         LEFT JOIN post_image pi ON job_post.post_id = pi.post_id\n" +
        "         LEFT JOIN contract ON contract.package_id = pack.package_id\n" +
        "         LEFT JOIN review ON review.contract_id = contract.contract_id\n" +
        "\n" +
        "WHERE (COALESCE(package_price,0) = (SELECT COALESCE(MIN(package_price),0)\n" +
        "                                    FROM job_package\n" +
        "                                    WHERE post_id = job_post.post_id))\n" +
        "  AND COALESCE((pi.image_id = (SELECT MIN(image_id)\n" +
        "                               FROM post_image\n" +
        "                               WHERE post_id = job_post.post_id)), TRUE)\n" +
        "GROUP BY job_post.post_id, package_price, pi.image_id")
public class JobCard implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private JobPost jobPost;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "min_rate_type", nullable = false)
    private JobPackage.RateType rateType;

    @Column(name = "min_pack_price")
    private Double price;

    @Column(name = "post_contract_count", nullable = false)
    private int contractsCompleted;

    @Column(name = "reviews")
    private int reviewsCount;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "card_image_id")
    private Long postImageId;

    /*default*/JobCard() {
    }

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, int contractsCompleted,
                   int reviewsCount, Double rating, Long postImageId) {
        this.jobPost = jobPost;
        this.rateType = rateType;
        this.price = price;
        this.contractsCompleted = contractsCompleted;
        this.reviewsCount = reviewsCount;
        this.rating = rating;
        this.postImageId = postImageId;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public JobPackage.RateType getRateType() {
        return rateType;
    }

    public Double getPrice() {
        return price;
    }

    public int getContractsCompleted() {
        return contractsCompleted;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public Double getRating() {
        return rating;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public void setRateType(JobPackage.RateType rateType) {
        this.rateType = rateType;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setContractsCompleted(int contractsCompleted) {
        this.contractsCompleted = contractsCompleted;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getPostImageId() {
        return postImageId;
    }

    public void setPostImageId(Long postImageId) {
        this.postImageId = postImageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobCard jobCard = (JobCard) o;
        return jobPost.equals(jobCard.jobPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobPost);
    }

    public enum OrderBy{
        MOST_HIRED("jobCard.orderBy.MOST_HIRED"),
        LEAST_HIRED("jobCard.orderBy.LEAST_HIRED"),
        BETTER_QUALIFIED("jobCard.orderBy.BETTER_QUALIFIED"),
        WORST_QUEALIFIED("jobCard.orderBy.WORST_QUEALIFIED"),
        NEWEST("jobCard.orderBy.NEWEST"),
        OLDEST("jobCard.orderBy.OLDEST");

        private final String stringCode;
        private final int value;

        OrderBy(final String stringCode) {
            this.stringCode = stringCode;
            this.value = ordinal();
        }

        public int getValue() {
            return value;
        }

        public String getStringCode() {
            return stringCode;
        }
    }
}
