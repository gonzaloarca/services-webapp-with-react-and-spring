package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
public class JobContract extends JobContractAbstract {

    @Column(name = "contract_image_type", length = 100)
    private String imageType;

    /*Default*/ JobContract() {
    }

    public JobContract(long id, User client, JobPackage jobPackage, LocalDateTime creationDate,
                       LocalDateTime scheduledDate, LocalDateTime lastModifiedDate, String description) {
        this.id = id;
        this.client = client;
        this.jobPackage = jobPackage;
        this.creationDate = creationDate;
        this.scheduledDate = scheduledDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
        this.state = ContractState.PENDING_APPROVAL;
    }

    public JobContract(JobContractWithImage jobContractWithImage) {
        this.id = jobContractWithImage.id;
        this.client = jobContractWithImage.client;
        this.jobPackage = jobContractWithImage.jobPackage;
        this.creationDate = jobContractWithImage.creationDate;
        this.scheduledDate = jobContractWithImage.scheduledDate;
        this.lastModifiedDate = jobContractWithImage.lastModifiedDate;
        this.description = jobContractWithImage.description;
        this.state = ContractState.PENDING_APPROVAL;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public enum ContractState {
        PENDING_APPROVAL("JobContract.ContractState.PendingApproval"),
        APPROVED("JobContract.ContractState.Approved"),
        CLIENT_REJECTED("JobContract.ContractState.ClientRejected"),
        PRO_REJECTED("JobContract.ContractState.ProRejected"),
        CLIENT_CANCELLED("JobContract.ContractState.ClientCancelled"),
        PRO_CANCELLED("JobContract.ContractState.ProCancelled"),
        COMPLETED("JobContract.ContractState.Completed"),
        CLIENT_MODIFIED("JobContract.ContractState.ClientModified"),
        PRO_MODIFIED("JobContract.ContractState.ProModified");

        final String stringCode;

        ContractState(String stringCode) {
            this.stringCode = stringCode;
        }

        public String getStringCode() {
            return stringCode;
        }
    }
}
