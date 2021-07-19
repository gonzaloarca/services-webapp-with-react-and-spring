package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobContractCard;

import java.net.URI;

public class JobCardOrderByDto {
    private Long id;
    private String description;


    public static JobCardOrderByDto fromJobCardOrderByInfo(long id, String description){
        JobCardOrderByDto jobCardOrderByDto = new JobCardOrderByDto();
        jobCardOrderByDto.id =id;
        jobCardOrderByDto.description = description;
        return jobCardOrderByDto;
    }

    public JobCardOrderByDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
