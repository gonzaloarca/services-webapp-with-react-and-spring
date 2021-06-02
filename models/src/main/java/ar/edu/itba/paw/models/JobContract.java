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
        PENDING_APPROVAL(1, "JobContract.ContractState.PendingApproval"),
        APPROVED(0, "JobContract.ContractState.Approved"),
        CLIENT_REJECTED(2, "JobContract.ContractState.ClientRejected"),
        PRO_REJECTED(2, "JobContract.ContractState.ProRejected"),
        CLIENT_CANCELLED(2, "JobContract.ContractState.ClientCancelled"),
        PRO_CANCELLED(2, "JobContract.ContractState.ProCancelled"),
        COMPLETED(2, "JobContract.ContractState.Completed"),
        CLIENT_MODIFIED(1, "JobContract.ContractState.ClientModified"),
        PRO_MODIFIED(1, "JobContract.ContractState.ProModified");

        final int category;
        final String stringCode;

        ContractState(int category, String stringCode) {
            this.category = category;
            this.stringCode = stringCode;
        }

        public int getCategory() {
            return category;
        }

        public String getStringCode() {
            return stringCode;
        }
    }
}
