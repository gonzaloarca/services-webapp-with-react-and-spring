package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewJobContractDto {

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = HirenetUtils.ISO_DATE_TIME_FORMAT)
    private String scheduledDate;

    @NotNull
    @Min(0)
    private Long clientId;

    @NotNull
    @Min(0)
    private Long jobPackageId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getJobPackageId() {
        return jobPackageId;
    }

    public void setJobPackageId(Long jobPackageId) {
        this.jobPackageId = jobPackageId;
    }
}
