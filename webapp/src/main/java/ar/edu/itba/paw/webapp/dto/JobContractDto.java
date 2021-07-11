package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobContract;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class JobContractDto {
    private Long id;
    private URI uri;
    private Long clientId;
    private JobPackageDto jobPackage;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime scheduledDate;
    private String description;
    private JobContractStateDto state;

    public static JobContractDto fromJobContract(JobContract jobContract, UriInfo uriInfo) {
        JobContractDto jobContractDto = new JobContractDto();
        jobContractDto.id = jobContract.getId();
        jobContractDto.clientId = jobContract.getClient().getId();
        jobContractDto.jobPackage = JobPackageDto.linkDataFromJobPackage(jobContract.getJobPackage(),uriInfo);
        jobContractDto.creationDate = jobContract.getCreationDate();
        jobContractDto.lastModifiedDate = jobContract.getLastModifiedDate();
        jobContractDto.scheduledDate = jobContract.getScheduledDate();
        jobContractDto.description = jobContract.getDescription();
        jobContractDto.state = JobContractStateDto.fromJobContractState(jobContract.getState());
        return jobContractDto;
    }

    public static JobContractDto linkDataFromJobContract(JobContract jobContract, UriInfo uriInfo) {
        JobContractDto jobContractDto = new JobContractDto();
        jobContractDto.id = jobContract.getId();
        jobContractDto.uri = uriInfo.getBaseUriBuilder()
                .path("/job-posts")
                .path(String.valueOf(jobContract.getJobPackage().getJobPost().getId()))
                .path("/packages")
                .path(String.valueOf(jobContract.getJobPackage().getId()))
                .path("/contracts")
                .path(String.valueOf(jobContract.getId())).build();
            //TODO: CHEQUEAR SI SE PUEDE EVITAR HARDCODEAR EL PREFIJO DE LA URI
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

    public JobContractStateDto getState() {
        return state;
    }

    public void setState(JobContractStateDto state) {
        this.state = state;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
