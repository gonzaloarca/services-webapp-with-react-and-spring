package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPost;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class JobPostDto {
    private Long id;
    private URI uri;
    private UserDto professional;
    private String title;
    private String availableHours;
    private JobTypeDto jobType;
    private Boolean isActive;
    private List<JobPostZoneDto> zones;
    private LocalDateTime creationDate;
    private List<JobPackageDto> packages;

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
        jobPostDto.jobType = JobTypeDto.fromJobType(jobPost.getJobType());
        jobPostDto.isActive = jobPost.isActive();
        jobPostDto.zones = jobPost.getZones().stream().map(JobPostZoneDto::fromJobPostZone).collect(Collectors.toList());
        jobPostDto.creationDate = jobPost.getCreationDate();
        jobPostDto.packages = jobPost.getJobPackages().stream().map(p->JobPackageDto.linkDataFromJobPackage(p,uriInfo)).collect(Collectors.toList());
        return jobPostDto;
    }
    public static JobPostDto linkDataFromJobPost(JobPost jobPost, UriInfo uriInfo) {
        final JobPostDto jobPostDto = new JobPostDto();
        jobPostDto.id = jobPost.getId();
        jobPostDto.uri = uriInfo.getBaseUriBuilder().path("/job-posts/")
                .path(String.valueOf(jobPost.getId())).build();
        //TODO CHEQUEAR SI SE PUEDE NO HARDCODEAR EL PREJIJO DE LA URI
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

    public JobTypeDto getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeDto jobType) {
        this.jobType = jobType;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getActive() {
        return isActive;
    }
    public List<JobPostZoneDto> getZones() {
        return zones;
    }

    public void setZones(List<JobPostZoneDto> zones) {
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


    public List<JobPackageDto> getPackages() {
        return packages;
    }

    public void setPackages(List<JobPackageDto> packages) {
        this.packages = packages;
    }
}
