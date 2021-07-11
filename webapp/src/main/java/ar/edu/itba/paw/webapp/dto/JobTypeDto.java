package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPost;

public class JobTypeDto {
    private int id;
    private String description;
    public static JobTypeDto fromJobType(JobPost.JobType jobType){
        JobTypeDto jobTypeDto = new JobTypeDto();
        jobTypeDto.id = jobType.getValue();
        jobTypeDto.description = jobType.getDescription();
        return jobTypeDto;
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
