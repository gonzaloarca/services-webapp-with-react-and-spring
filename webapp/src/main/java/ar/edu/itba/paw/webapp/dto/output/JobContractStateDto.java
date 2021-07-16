package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.JobContract;

public class JobContractStateDto {
    private Long id;
    private String description;
    public static JobContractStateDto fromJobContractState(JobContract.ContractState jobContractState){
        JobContractStateDto jobContractStateDto = new JobContractStateDto();
        jobContractStateDto.id = jobContractState.getId();
        jobContractStateDto.description = jobContractState.getDescription();
        return jobContractStateDto;
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
