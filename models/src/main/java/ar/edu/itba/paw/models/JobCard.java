package ar.edu.itba.paw.models;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "job_cards")
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_image_id")
    private JobPostImage postImage;

    /*default*/JobCard(){}

    public JobCard(JobPost jobPost, JobPackage.RateType rateType, Double price, JobPostImage postImage, int contractsCompleted, int reviewsCount) {
        this.jobPost = jobPost;
        this.rateType = rateType;
        this.price = price;
        this.contractsCompleted = contractsCompleted;
        this.reviewsCount = reviewsCount;
        this.postImage = postImage;
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

    public JobPostImage getPostImage() {
        return postImage;
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

    public void setPostImage(JobPostImage postImage) {
        this.postImage = postImage;
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
}
