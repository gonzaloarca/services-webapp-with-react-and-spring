package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobContract;

import java.time.LocalDateTime;

public class JobContractDto {
    private long id;
    private UserDto client;
    private JobPackageDto jobPackage;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime scheduledDate;
    private String description;
    private JobContract.ContractState state;

    public static JobContractDto fromJobContract(JobContract jobContract) {
        JobContractDto jobContractDto = new JobContractDto();
        jobContractDto.id = jobContract.getId();
        jobContractDto.client = UserDto.fromUser(jobContract.getClient());
        jobContractDto.creationDate = jobContract.getCreationDate();
        jobContractDto.lastModifiedDate = jobContract.getLastModifiedDate();
        jobContractDto.scheduledDate = jobContract.getScheduledDate();
        jobContractDto.description = jobContract.getDescription();
        jobContractDto.state = jobContract.getState();
        return jobContractDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getClient() {
        return client;
    }

    public void setClient(UserDto client) {
        this.client = client;
    }

    public JobPackageDto getJobPackage() {
        return jobPackage;
    }

    public void setJobPackage(JobPackageDto jobPackage) {
        this.jobPackage = jobPackage;
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
}
