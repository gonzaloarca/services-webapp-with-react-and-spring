package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPost;

import java.time.LocalDateTime;
import java.util.List;

public class JobPostDto {
    private long id;
    private UserDto userDto;
    private String title;
    private String availableHours;
    private JobPost.JobType jobType;
    private boolean isActive;
    private List<JobPost.Zone> zones;
    private LocalDateTime creationDate;
    private long ratesQuantity;
    private Double avgRate;
    private long contractsCompleted;

    public static JobPostDto fromJobPost(JobPost jobPost) {
        final JobPostDto jobPostDto = new JobPostDto();
        jobPostDto.id = jobPost.getId();
        jobPostDto.userDto = UserDto.fromUser(jobPost.getUser());
        jobPostDto.title = jobPost.getTitle();
        jobPostDto.availableHours = jobPost.getAvailableHours();
        jobPostDto.jobType = jobPost.getJobType();
        jobPostDto.isActive = jobPost.isActive();
        jobPostDto.zones = jobPost.getZones();
        jobPostDto.creationDate = jobPost.getCreationDate();
        return jobPostDto;
    }

    public static JobPostDto fromJobPostWithExtraData(JobPost jobPost, long ratesQuantity, Double avgRate, long contractsCompleted) {
        final JobPostDto jobPostDto = fromJobPost(jobPost);
        jobPostDto.ratesQuantity = ratesQuantity;
        jobPostDto.avgRate = avgRate;
        jobPostDto.contractsCompleted = contractsCompleted;
        return jobPostDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public long getRatesQuantity() {
        return ratesQuantity;
    }

    public void setRatesQuantity(long ratesQuantity) {
        this.ratesQuantity = ratesQuantity;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public long getContractsCompleted() {
        return contractsCompleted;
    }

    public void setContractsCompleted(long contractsCompleted) {
        this.contractsCompleted = contractsCompleted;
    }
}
