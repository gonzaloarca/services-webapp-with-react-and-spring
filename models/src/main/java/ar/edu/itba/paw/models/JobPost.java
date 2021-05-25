package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
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


    @ElementCollection(targetClass = JobPost.Zone.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "post_zone", joinColumns = {@JoinColumn(name = "post_id", nullable = false)},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "zone_id"}, name = "post_zone_pkey")},
            foreignKey = @ForeignKey(name = "post_zone_post_id_fkey"))
    @Column(name = "zone_id", nullable = false)
    private List<Zone> zones;

    @Column(name = "post_creation_date", nullable = false)
    private LocalDateTime creationDate;

    /*default*/ JobPost() {
    }

    public JobPost(User user, String title, String availableHours, JobType jobType, List<Zone> zones, LocalDateTime creationDate) {
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.creationDate = creationDate;
        this.isActive = true;
    }

    //Constructor para crear un post nuevo (esta activo)
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, LocalDateTime creationDate) {
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
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, boolean isActive, LocalDateTime creationDate) {
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

    public List<Zone> getZones() {
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

    public void setZones(List<Zone> zones) {
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

    public enum Zone {
        AGRONOMIA("JobPost.Zone.AGRONOMIA"),
        ALMAGRO("JobPost.Zone.ALMAGRO"),
        BALVANERA("JobPost.Zone.BALVANERA"),
        BARRACAS("JobPost.Zone.BARRACAS"),
        BELGRANO("JobPost.Zone.BELGRANO"),
        BOEDO("JobPost.Zone.BOEDO"),
        CABALLITO("JobPost.Zone.CABALLITO"),
        CHACARITA("JobPost.Zone.CHACARITA"),
        COGHLAN("JobPost.Zone.COGHLAN"),
        COLEGIALES("JobPost.Zone.COLEGIALES"),
        CONSTITUCION("JobPost.Zone.CONSTITUCION"),
        FLORES("JobPost.Zone.FLORES"),
        FLORESTA("JobPost.Zone.FLORESTA"),
        BOCA("JobPost.Zone.BOCA"),
        PATERNAL("JobPost.Zone.PATERNAL"),
        LINIERS("JobPost.Zone.LINIERS"),
        MATADEROS("JobPost.Zone.MATADEROS"),
        MONTECASTRO("JobPost.Zone.MONTECASTRO"),
        MONSERRAT("JobPost.Zone.MONSERRAT"),
        NPOMPEYA("JobPost.Zone.NPOMPEYA"),
        NUNIEZ("JobPost.Zone.NUNIEZ"),
        PALERMO("JobPost.Zone.PALERMO"),
        PAVELLANEDA("JobPost.Zone.PAVELLANEDA"),
        PCHACABUCO("JobPost.Zone.PCHACABUCO"),
        PCHAS("JobPost.Zone.PCHAS"),
        PATRICIOS("JobPost.Zone.PATRICIOS"),
        MADERO("JobPost.Zone.MADERO"),
        RECOLETA("JobPost.Zone.RECOLETA"),
        RETIRO("JobPost.Zone.RETIRO"),
        SAAVEDRA("JobPost.Zone.SAAVEDRA"),
        SANCRISTOBAL("JobPost.Zone.SANCRISTOBAL"),
        SANNICOLAS("JobPost.Zone.SANNICOLAS"),
        SANTELMO("JobPost.Zone.SANTELMO"),
        VELEZ("JobPost.Zone.VELEZ"),
        VERSALLES("JobPost.Zone.VERSALLES"),
        CRESPO("JobPost.Zone.CRESPO"),
        VPARQUE("JobPost.Zone.VPARQUE"),
        DEVOTO("JobPost.Zone.DEVOTO"),
        MITRE("JobPost.Zone.MITRE"),
        LUGANO("JobPost.Zone.LUGANO"),
        LURO("JobPost.Zone.LURO"),
        ORTUZAR("JobPost.Zone.ORTUZAR"),
        PUEYRREDON("JobPost.Zone.PUEYRREDON"),
        VREAL("JobPost.Zone.VREAL"),
        RIACHUELO("JobPost.Zone.RIACHUELO"),
        SANTARITA("JobPost.Zone.SANTARITA"),
        SOLDATI("JobPost.Zone.SOLDATI"),
        URQUIZA("JobPost.Zone.URQUIZA");

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
