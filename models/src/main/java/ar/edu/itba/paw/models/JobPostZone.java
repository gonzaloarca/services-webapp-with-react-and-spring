package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "post_zone", uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "zone_id"}, name = "job_zone_pkey")})
public class JobPostZone implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "post_zone_post_id_fkey"))
    private JobPost post;

    @Id
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "zone_id")
    private Zone zone;

    /*default*/ JobPostZone() {}

    public JobPostZone(Zone zone) {
        this.zone = zone;
    }

    public JobPost getPost() {
        return post;
    }

    public Zone getZone() {
        return zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobPostZone that = (JobPostZone) o;
        return zone == that.zone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone);
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
