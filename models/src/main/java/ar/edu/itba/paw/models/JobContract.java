package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "contract")
public class JobContract extends JobContractAbstract{

    @Column(name = "contract_image_type", length = 100)
    private String imageType;

    /*Default*/ JobContract() {
    }

    public JobContract(long id, User client, JobPackage jobPackage, LocalDateTime creationDate, String description) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.description = description;
        this.state = ContractState.PENDING_APPROVAL;
    }

    public JobContract(JobContractWithImage jobContractWithImage) {
        this.id = jobContractWithImage.id;
        this.client = jobContractWithImage.client;
        this.jobPackage = jobContractWithImage.jobPackage;
        this.creationDate = jobContractWithImage.creationDate;
        this.description = jobContractWithImage.description;
        this.state = ContractState.PENDING_APPROVAL;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    //TODO Refactor: mover esto a JobContractAbstract?
    public enum ContractState {
        PENDING_APPROVAL(1),
        APPROVED(0),
        CLIENT_REJECTED(2),
        PRO_REJECTED(2),
        CLIENT_CANCELLED(2),
        PRO_CANCELLED(2),
        COMPLETED(2),
        CLIENT_MODIFIED(1),
        PRO_MODIFIED(1);

        final int category;

        ContractState(int category) {
            this.category = category;
        }

        public int getCategory() {
            return category;
        }
    }
}
