package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPost;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class JobPostDto {
    private Long id;
    private URI uri;
    private UserDto professional;
    private String title;
    private String availableHours;
    private JobPost.JobType jobType;
    private Boolean isActive;
    private List<JobPost.Zone> zones;
    private LocalDateTime creationDate;

    public static JobPostDto fromJobPost(JobPost jobPost, UriInfo uriInfo) {
        final JobPostDto jobPostDto = new JobPostDto();
        jobPostDto.id = jobPost.getId();
        jobPostDto.professional = new UserDto();
        jobPostDto.professional.setId(jobPost.getUser().getId());
        jobPostDto.professional.setUri(uriInfo.getBaseUriBuilder().path("/users/")
                //TODO: CHEQUEAR SI SE PUEDE EVITAR HARDCODEAR EL PREFIJO DE LA URI
                .path(String.valueOf(jobPost.getUser().getId())).build());
        jobPostDto.title = jobPost.getTitle();
        jobPostDto.availableHours = jobPost.getAvailableHours();
        jobPostDto.jobType = jobPost.getJobType();
        jobPostDto.isActive = jobPost.isActive();
        jobPostDto.zones = jobPost.getZones();
        jobPostDto.creationDate = jobPost.getCreationDate();
        return jobPostDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getProfessional() {
        return professional;
    }

    public void setProfessional(UserDto professional) {
        this.professional = professional;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public JobPost.JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobPost.JobType jobType) {
        this.jobType = jobType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<JobPost.Zone> getZones() {
        return zones;
    }

    public void setZones(List<JobPost.Zone> zones) {
        this.zones = zones;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
