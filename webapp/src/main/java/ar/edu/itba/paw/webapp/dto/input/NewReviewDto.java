package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewReviewDto {
    @NotBlank
    @Size(max = 100)
    private String description;

    @NotNull
    private int rateValue;

    @Size(max = 100)
    private String title;

    @NotNull
    @Min(0)
    private Long jobContractId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rate) {
        this.rateValue = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getJobContractId() {
        return jobContractId;
    }

    public void setJobContractId(Long jobContractId) {
        this.jobContractId = jobContractId;
    }
}
