package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class ReviewForm {
    @NotBlank
    @Size(max = 100)
    private String description;

    private int rateValue = 1;

    @Size(max = 100)
    private String title;

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
}
