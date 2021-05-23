package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job_post")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_post_post_id_seq")
    @SequenceGenerator(sequenceName = "job_post_post_id_seq", name = "job_post_post_id_seq", allocationSize = 1)
    @Column(name = "post_id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "job_post_user_id_fkey"))
    private User user;

    @Column(length = 100, nullable = false, name = "post_title")
    private String title;

    @Column(length = 100, nullable = false, name = "post_available_hours")
    private String availableHours;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "post_job_type", nullable = false)
    private JobType jobType;

    @Column(name = "post_is_active", nullable = false)
    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "post")
    private List<JobPostZone> zones;

    @Column(name = "post_creation_date", nullable = false)
    private LocalDateTime creationDate;

    /*default*/ JobPost() {
    }

    public JobPost(User user, String title, String availableHours, JobType jobType, List<JobPostZone> zones, LocalDateTime creationDate) {
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.creationDate = creationDate;
        this.isActive = true;
    }

    //Constructor para crear un post nuevo (esta activo)
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<JobPostZone> zones, LocalDateTime creationDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.creationDate = creationDate;
        this.isActive = true;
    }

    //Constructor para crear un post que puede no estar activo
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<JobPostZone> zones, boolean isActive, LocalDateTime creationDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.isActive = isActive;
        this.creationDate = creationDate;
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

    public List<JobPostZone> getZones() {
        return zones;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setZones(List<JobPostZone> zones) {
        this.zones = zones;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

}
