package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class JobContract {
    private final long id;
    private final User client;
    private final JobPackage jobPackage;
    private final User professional;
    private final LocalDateTime creationDate;
    private final String description;
    private final ByteImage image;

    public JobContract(long id, User client, JobPackage jobPackage, User professional, LocalDateTime creationDate, String description,
                       ByteImage image) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.professional = professional;
        this.creationDate = creationDate;
        this.description = description;
        this.image = image;
    }

    public JobPackage getJobPackage() {
        return jobPackage;
    }

    public long getId() {
        return id;
    }

    public User getClient() {
        return client;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public User getProfessional() {
        return professional;
    }

    public ByteImage getImage() {
        return image;
    }


    @Override
    public String toString() {
        return "JobContract{" +
                "id=" + id +
                ", client=" + client +
                ", jobPackage=" + jobPackage +
                ", professional=" + professional +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobContract that = (JobContract) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
