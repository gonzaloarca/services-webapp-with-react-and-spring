package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
public class JobContract extends JobContractAbstract {

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
        this.state = jobContractWithImage.state;
    }

    public enum ContractState {
        PENDING_APPROVAL("PENDING_APPROVAL"),
        APPROVED("APPROVED"),
        CLIENT_REJECTED("CLIENT_REJECTED"),
        PRO_REJECTED("PRO_REJECTED"),
        CLIENT_CANCELLED("CLIENT_CANCELLED"),
        PRO_CANCELLED("PRO_CANCELLED"),
        COMPLETED("COMPLETED"),
        CLIENT_MODIFIED("CLIENT_MODIFIED"),
        PRO_MODIFIED("PRO_MODIFIED");

        private final String description;
        private final long id;

        ContractState(String description) {
            this.description = description;
            this.id = ordinal();
        }

        public String getDescription() {
            return description;
        }

        public long getId() {
            return id;
        }
    }
}
