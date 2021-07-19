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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "jobPost",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<JobPackage> jobPackages;

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

    public List<JobPackage> getJobPackages() {
        return jobPackages;
    }

    public void setJobPackages(List<JobPackage> jobPackages) {
        this.jobPackages = jobPackages;
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
        private String description;
        private int value;

        JobType(String imagePath, String stringCode) {
            this.imagePath = imagePath;
            this.description = stringCode;
            this.value = ordinal();
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String stringCode) {
            this.description = stringCode;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public enum Zone {
        AGRONOMIA("Agronomia"),
        ALMAGRO("Almagro"),
        BALVANERA("Balvanera"),
        BARRACAS("Barracas"),
        BELGRANO("Belgrano"),
        BOEDO("Boedo"),
        CABALLITO("Caballito"),
        CHACARITA("Chacarita"),
        COGHLAN("Coghlan"),
        COLEGIALES("Colegiales"),
        CONSTITUCION("Constitucion"),
        FLORES("Flores"),
        FLORESTA("Floresta"),
        BOCA("Boca"),
        PATERNAL("Paternal"),
        LINIERS("Liniers"),
        MATADEROS("Mataderos"),
        MONTECASTRO("Montecastro"),
        MONSERRAT("Monserrat"),
        NPOMPEYA("Nueva Pompeya"),
        NUNIEZ("Nuñez"),
        PALERMO("Palermo"),
        PAVELLANEDA("Parque Avellaneda"),
        PCHACABUCO("Parque Chacabuco"),
        PCHAS("Parque Chas"),
        PATRICIOS("Parque Patricios"),
        MADERO("Puerto Madero"),
        RECOLETA("Recoleta"),
        RETIRO("Retiro"),
        SAAVEDRA("Saavedra"),
        SANCRISTOBAL("San Cristobal"),
        SANNICOLAS("San Nicolas"),
        SANTELMO("San Telmo"),
        VELEZ("Velez"),
        VERSALLES("Versalles"),
        CRESPO("Villa Crespo"),
        VPARQUE("Villa del Parque"),
        DEVOTO("Villa Devoto"),
        MITRE("Villa General Mitre"),
        LUGANO("Villa Lugano"),
        LURO("Villa Luro"),
        ORTUZAR("Villa Ortuzar"),
        PUEYRREDON("Villa Pueyrredón"),
        VREAL("Villa Real"),
        RIACHUELO("Villa Riachuelo"),
        SANTARITA("Villa Santa Rita"),
        SOLDATI("Villa Soldati"),
        URQUIZA("Villa Urquiza");

        private final String description;
        private final int value;

        Zone(final String stringCode) {
            this.description = stringCode;
            this.value = ordinal();
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }
}
