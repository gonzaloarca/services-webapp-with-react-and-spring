package ar.edu.itba.paw.models;

import java.util.List;
import java.util.Objects;

public class JobPost {
    private final long id;
    private final User user;
    private final String title;
    private final String availableHours;
    private final JobType jobType;
    private final boolean isActive;
    private final List<Zone> zones;
    private final double rating;

    //Constructor para crear un post nuevo (esta activo)
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, double rating) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.rating = rating;
        this.isActive = true;
    }

    //Constructor para crear un post que puede no estar activo
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, double rating, boolean isActive) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.rating = rating;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public JobType getJobType() {
        return jobType;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public double getRating() {
        return rating;
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

        private final String stringCode;
        private final int value;

        Zone(final String stringCode) {
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
