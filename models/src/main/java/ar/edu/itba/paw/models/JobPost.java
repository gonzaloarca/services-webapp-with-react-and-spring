package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
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
    private final LocalDateTime creationDate;

    //Constructor para crear un post nuevo (esta activo)
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, double rating, LocalDateTime creationDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.rating = rating;
        this.creationDate = creationDate;
        this.isActive = true;
    }

    //Constructor para crear un post que puede no estar activo
    public JobPost(long id, User user, String title, String availableHours, JobType jobType, List<Zone> zones, double rating, boolean isActive, LocalDateTime creationDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.availableHours = availableHours;
        this.jobType = jobType;
        this.zones = zones;
        this.rating = rating;
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

    public double getRating() {
        return rating;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
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
