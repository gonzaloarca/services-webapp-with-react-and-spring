package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.JobPost;

public class JobTypeDto {
    private int id;
    private String description;

    public static JobTypeDto fromJobTypeWithLocalizedMessage(JobPost.JobType jobType, String message){
        JobTypeDto jobTypeDto = new JobTypeDto();
        jobTypeDto.id = jobType.getValue();
        jobTypeDto.description = message;
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
