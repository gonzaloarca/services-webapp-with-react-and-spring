package ar.edu.itba.paw.models;

import java.util.Date;

public class JobContract {
    private long id;
    private User client;
    private JobPackage jobPackage;
    private User professional;
    private Date creationDate;           //TODO: ver tipo de variable
    private String description;
    private String image;

    public JobContract() {
    }

    public JobContract(User client, JobPackage jobPackage, String description, String image) {
        this.client = client;
        this.jobPackage = jobPackage;
        this.description = description;
        this.image = image;
    }

    public JobContract(long id, User client, JobPackage jobPackage, Date creationDate, String description, String image) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.description = description;
        this.image = image;
    }

    public JobContract(long id, User client, JobPackage jobPackage, User professional, Date creationDate, String description, String image) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getProfessional() {
        return professional;
    }

    public void setProfessional(User professional) {
        this.professional = professional;
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
                ", image='" + image + '\'' +
                '}';
    }
}
