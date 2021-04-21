package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class ReviewForm {
    @NotBlank
    @Size(max = 100)
    private String description;

    private int rateForm = 1;

    @Size(max = 100)
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRateForm() {
        return rateForm;
    }

    public void setRateForm(int rate) {
        this.rateForm = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
