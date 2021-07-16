package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPost;

public class JobPostZoneDto {
    private int id;
    private String description;

    public static JobPostZoneDto fromJobPostZone(JobPost.Zone jobPostZone){
        JobPostZoneDto jobPostZoneDto = new JobPostZoneDto();
        jobPostZoneDto.id = jobPostZone.getValue();
        jobPostZoneDto.description = jobPostZone.getDescription();
        return jobPostZoneDto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
