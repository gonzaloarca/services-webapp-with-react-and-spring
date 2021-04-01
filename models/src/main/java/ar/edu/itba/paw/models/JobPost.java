package ar.edu.itba.paw.models;

public class JobPost {
    private long id;
    private long userId;
    private String title;
    private String availableHours;
    private JobType jobType;
    private boolean isActive;

    public JobPost() {
    }

    public JobPost(long userId, String title, String availableHours, JobType jobType) {
        this.userId = userId;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.isActive = true;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return id + ": " + title;
    }

    public enum JobType {
        PLUMBING,
        ELECTRICITY,
        CARPENTRY,
        CATERING,
        PAINTING,
        TEACHING,
        CLEANING,
        BABYSITTING
    }
}
