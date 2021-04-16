package ar.edu.itba.paw.models;

import java.util.Date;
import java.util.Objects;

public class JobContract {
    private long id;
    private User client;
    private JobPackage jobPackage;
    private User professional;
    private Date creationDate;           //TODO: ver tipo de variable
    private String description;
    private byte[] imageData;

    public JobContract() {
    }

    //TODO: ver si este constructor está de mas, se usa en los tests
    public JobContract(long id, User client, JobPackage jobPackage, User professional, Date creationDate, String description) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.professional = professional;
        this.creationDate = creationDate;
        this.description = description;
    }

    public JobContract(long id, User client, JobPackage jobPackage, User professional, Date creationDate, String description, byte[] imageData) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.professional = professional;
        this.creationDate = creationDate;
        this.description = description;
        this.imageData = imageData;
    }

    public JobPackage getJobPackage() {
        return jobPackage;
    }

    public void setJobPackage(JobPackage jobPackage) {
        this.jobPackage = jobPackage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getProfessional() {
        return professional;
    }

    public void setProfessional(User professional) {
        this.professional = professional;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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
