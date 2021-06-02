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
    private ByteImage image;

    /*default*/JobContractWithImage() {

    }

    public JobContractWithImage(User client, JobPackage jobPackage, LocalDateTime creationDate,
                                String description, ByteImage image) {
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.description = description;
        this.state = JobContract.ContractState.PENDING_APPROVAL;
        this.image = image;
    }

    public JobContractWithImage(long id, User client, JobPackage jobPackage, LocalDateTime creationDate,
                                String description, ByteImage image) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.description = description;
        this.state = JobContract.ContractState.PENDING_APPROVAL;
        this.image = image;
    }

    public ByteImage getImage() {
        return image;
    }

    public void setImage(ByteImage image) {
        this.image = image;
    }
}
