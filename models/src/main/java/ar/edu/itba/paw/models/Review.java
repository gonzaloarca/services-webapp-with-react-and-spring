package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "review")
public class Review implements Serializable {
    @Column(length = 100, nullable = false, name = "review_rate")
    private int rate;

    @Column(length = 100, name = "review_title")
    private String title;

    @Column(length = 100, nullable = false, name = "review_description")
    private String description;

    // TODO: Si se borra JobContract que se borre la review
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", foreignKey = @ForeignKey(name = "review_contract_id_fkey"), nullable = false)
    private JobContract jobContract;

    @Column(name = "review_creation_date")
    private LocalDateTime creationDate;

    /* default */ Review() {
    }

    public Review(int rate, String title, String description, JobContract contract, LocalDateTime creationDate) {
        this.rate = rate;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.jobContract = contract;
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
        return jobContract.getClient();
    }

    public JobPost getJobPost() {
        return jobContract.getJobPackage().getJobPost();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public JobContract getJobContract() {
        return jobContract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(jobContract, review.jobContract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobContract);
    }
}
