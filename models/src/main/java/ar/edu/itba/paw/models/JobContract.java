package ar.edu.itba.paw.models;

import java.util.Date;

public class JobContract {
    private long id;
    private long postId;
    private long clientId;
    private long packageId;
    private Date creationDate;           //TODO: ver tipo de variable
    private String description;
    private String image;

    public JobContract() {
    }

    public JobContract(long postId, long clientId, String description, String image) {
        this.postId = postId;
        this.clientId = clientId;
        this.description = description;
        this.image = image;
    }



    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
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

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
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
}
