package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.JobContract;
import ar.edu.itba.paw.models.JobContractWithImage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class JobContractDto {
    private Long id;
    private Long clientId;
    private URI jobPackage;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime scheduledDate;
    private String description;
    private JobContractStateDto state;
    private URI image;
    private boolean wasRescheduled;

    public static JobContractDto fromJobContract(JobContractWithImage jobContract, UriInfo uriInfo) {
        JobContractDto jobContractDto = new JobContractDto();
        jobContractDto.id = jobContract.getId();
        jobContractDto.clientId = jobContract.getClient().getId();
        jobContractDto.jobPackage = uriInfo.getBaseUriBuilder()
                .path("/job-posts/").path(String.valueOf(jobContract.getJobPackage().getPostId()))
                .path("/packages").path(String.valueOf(jobContract.getJobPackage().getId())).build();
        jobContractDto.creationDate = jobContract.getCreationDate();
        jobContractDto.lastModifiedDate = jobContract.getLastModifiedDate();
        jobContractDto.scheduledDate = jobContract.getScheduledDate();
        jobContractDto.description = jobContract.getDescription();
        jobContractDto.state = JobContractStateDto.fromJobContractState(jobContract.getState());
        jobContractDto.image = jobContract.getByteImage() != null ? uriInfo.getBaseUriBuilder().path("/contracts")
                .path(String.valueOf(jobContractDto.id)).path("/image").build() : null;
        jobContractDto.wasRescheduled = jobContract.isWasRescheduled();
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

    public URI getJobPackage() {
        return jobPackage;
    }

    public void setJobPackage(URI jobPackage) {
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

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public boolean isWasRescheduled() {
        return wasRescheduled;
    }

    public void setWasRescheduled(boolean wasRescheduled) {
        this.wasRescheduled = wasRescheduled;
    }
}
