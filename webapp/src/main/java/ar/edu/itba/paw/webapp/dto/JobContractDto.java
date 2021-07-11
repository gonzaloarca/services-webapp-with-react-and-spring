package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobContract;

import java.net.URI;
import java.time.LocalDateTime;

public class JobContractDto {
    private Long id;
    private URI uri;
    private Long clientId;
    private Long jobPackageId;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime scheduledDate;
    private String description;
    private JobContract.ContractState state;

    public static JobContractDto fromJobContract(JobContract jobContract) {
        JobContractDto jobContractDto = new JobContractDto();
        jobContractDto.id = jobContract.getId();
        jobContractDto.clientId = jobContract.getClient().getId();
        jobContractDto.jobPackageId = jobContract.getJobPackage().getId();
        jobContractDto.creationDate = jobContract.getCreationDate();
        jobContractDto.lastModifiedDate = jobContract.getLastModifiedDate();
        jobContractDto.scheduledDate = jobContract.getScheduledDate();
        jobContractDto.description = jobContract.getDescription();
        jobContractDto.state = jobContract.getState();
        return jobContractDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getJobPackageId() {
        return jobPackageId;
    }

    public void setJobPackageId(Long jobPackageId) {
        this.jobPackageId = jobPackageId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobContract.ContractState getState() {
        return state;
    }

    public void setState(JobContract.ContractState state) {
        this.state = state;
    }

    public URI getJobContractURI() {
        return uri;
    }

    public void setJobContractURI(URI jobContractURI) {
        this.uri = jobContractURI;
    }
}
