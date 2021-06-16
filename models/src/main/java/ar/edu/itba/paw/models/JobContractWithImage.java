package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
public class JobContractWithImage extends JobContractAbstract{

    @AttributeOverrides({
            @AttributeOverride(name = "data", column = @Column(name = "image_data")),
            @AttributeOverride(name = "type", column = @Column(name = "contract_image_type", length = 100))
    })
    private ByteImage byteImage;

    /*default*/JobContractWithImage() {

    }

    public JobContractWithImage(User client, JobPackage jobPackage, LocalDateTime creationDate,
                                LocalDateTime scheduledDate, LocalDateTime lastModifiedDate,
                                String description, ByteImage image) {
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.scheduledDate = scheduledDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
        this.state = JobContract.ContractState.PENDING_APPROVAL;
        this.byteImage = image;
    }

    public JobContractWithImage(long id, User client, JobPackage jobPackage, LocalDateTime creationDate,
                                LocalDateTime scheduledDate, LocalDateTime lastModifiedDate,
                                String description, ByteImage image) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.scheduledDate = scheduledDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
        this.state = JobContract.ContractState.PENDING_APPROVAL;
        this.byteImage = image;
    }

    public ByteImage getByteImage() {
        return byteImage;
    }

    public void setByteImage(ByteImage byteImage) {
        this.byteImage = byteImage;
    }
}
