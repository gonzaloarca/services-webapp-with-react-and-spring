package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.JobCard;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

import static ar.edu.itba.paw.interfaces.HirenetUtils.SEARCH_WITHOUT_CATEGORIES;

public class SearchForm {

    @NotBlank
    @Size(max = 100)
    private String zone;

    @Size(max = 100)
    private String query;

    private int category = SEARCH_WITHOUT_CATEGORIES;

    private int orderBy = JobCard.OrderBy.BETTER_QUALIFIED.ordinal();

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }
}
