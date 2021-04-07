package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SearchForm {

    @Pattern(regexp = "^[0-9][0-9]*$")
    @NotBlank
    private String zone;

    @Size(max = 200)
    private String query;

    @Pattern(regexp = "^[0-9][0-9]*$")
    private String category;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
