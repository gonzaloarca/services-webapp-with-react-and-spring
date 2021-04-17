package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobPost {
    private long id;
    private User user;
    private String title;
    private String availableHours;
    private JobType jobType;
    private boolean isActive;
    private List<Zone> zones;
    private List<JobPostImage> images;

    public JobPost() {
    }

    //Constructor para crear un post nuevo (esta activo)
    public JobPost(User user, String title, String availableHours, JobType jobType, List<Zone> zones) {
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.isActive = true;
        this.images = new ArrayList<>();
    }

    //Constructor para crear un post que puede no estar activo
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, boolean isActive) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.isActive = isActive;
        this.images = new ArrayList<>();
    }

    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, List<JobPostImage> images) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.isActive = true;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<JobPostImage> getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "JobPost{" +
                "id=" + id +
                ", userId=" + user +
                ", title='" + title + '\'' +
                ", availableHours='" + availableHours + '\'' +
                ", jobType=" + jobType +
                ", isActive=" + isActive +
                ", zones=" + zones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPost jobPost = (JobPost) o;
        return id == jobPost.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum JobType {
        PLUMBING("plumbing.jpeg", "JobPost.JobType.PLUMBING"),
        ELECTRICITY("electrician.jpeg", "JobPost.JobType.ELECTRICITY"),
        CARPENTRY("carpentry.jpeg", "JobPost.JobType.CARPENTRY"),
        CATERING("catering.png", "JobPost.JobType.CATERING"),
        PAINTING("painter.jpeg", "JobPost.JobType.PAINTING"),
        TEACHING("teaching.jpeg", "JobPost.JobType.TEACHING"),
        CLEANING("cleaning.png", "JobPost.JobType.CLEANING"),
        BABYSITTING("babysitting.jpeg", "JobPost.JobType.BABYSITTING");

        private String imagePath;
        private String stringCode;
        private int value;

        JobType(String imagePath, String stringCode) {
            this.imagePath = imagePath;
            this.stringCode = stringCode;
            this.value = ordinal();
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getStringCode() {
            return stringCode;
        }

        public void setStringCode(String stringCode) {
            this.stringCode = stringCode;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public enum Zone {
        BELGRANO("JobPost.Zone.BELGRANO"),
        PALERMO("JobPost.Zone.PALERMO"),
        RETIRO("JobPost.Zone.RETIRO"),
        NUNIEZ("JobPost.Zone.NUNIEZ"),
        COLEGIALES("JobPost.Zone.COLEGIALES");

        private String stringCode;
        private int value;

        Zone(final String stringCode) {
            this.stringCode = stringCode;
            this.value = ordinal();
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getStringCode() {
            return stringCode;
        }

        public void setStringCode(String stringCode) {
            this.stringCode = stringCode;
        }
    }
}
