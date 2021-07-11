package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.JobPackage;

public class JobPackageRateTypeDto {
    private String description;
    private Long id;
    public static JobPackageRateTypeDto fromJobPackageRateType(JobPackage.RateType jobPackageRateType){
        JobPackageRateTypeDto jobPackageRateTypeDto = new JobPackageRateTypeDto();
        jobPackageRateTypeDto.description = jobPackageRateType.getDescription();
        jobPackageRateTypeDto.id = jobPackageRateType.getId();
        return jobPackageRateTypeDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
