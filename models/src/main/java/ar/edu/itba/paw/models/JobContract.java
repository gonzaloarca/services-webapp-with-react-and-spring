package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "contract")
public class JobContract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_contract_id_seq")
    @SequenceGenerator(sequenceName = "contract_contract_id_seq", name = "contract_contract_id_seq", allocationSize = 1)
    @Column(name = "contract_id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "contract_client_id_fkey"))
    private User client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_id", foreignKey = @ForeignKey(name = "contract_package_id_fkey"))
    private JobPackage jobPackage;

    @Column(length = 100, name = "contract_creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(length = 100, name = "contract_description", nullable = false)
    private String description;

    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "image_data")),
            @AttributeOverride(name = "type", column = @Column(name = "contract_image_type", length = 100))
    })
    private ByteImage image;

    @Transient
    private EncodedImage encodedImage;    //Se setea en el Dao

    /*Default*/ JobContract() {}

    public JobContract(long id, User client, JobPackage jobPackage, LocalDateTime creationDate, String description,
                       ByteImage image) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.description = description;
        this.image = image;
        this.encodedImage = new EncodedImage(null, null);
    }

    public JobContract(User client, JobPackage jobPackage, LocalDateTime creationDate, String description,
                       ByteImage image, EncodedImage encodedImage) {
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.description = description;
        this.image = image;
        this.encodedImage = encodedImage;
    }

    public JobPackage getJobPackage() {
        return jobPackage;
    }

    public long getId() {
        return id;
    }

    public User getClient() {
        return client;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public User getProfessional() {
        return jobPackage.getJobPost().getUser();
    }

    public ByteImage getImage() {
        return image;
    }

    public EncodedImage getEncodedImage() {
        return encodedImage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setJobPackage(JobPackage jobPackage) {
        this.jobPackage = jobPackage;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(ByteImage image) {
        this.image = image;
    }

    public void setEncodedImage(EncodedImage encodedImage) {
        this.encodedImage = encodedImage;
    }

    @Override
    public String toString() {
        return "JobContract{" +
                "id=" + id +
                ", client=" + client +
                ", jobPackage=" + jobPackage +
                ", professional=" + getProfessional() +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobContract that = (JobContract) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
