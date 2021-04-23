package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.PriceNotEmpty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PriceNotEmpty
public class PackageForm {
    @NotEmpty
    @Size(max = 100)
    private String title;

    @NotEmpty
    @Size(max = 100)
    private String description;

    @NotNull
    private Integer rateType;

    // Si fuera double y dejamos el campo vacío, tira una excepcion en el lugar del mensaje de error en la pagina
    @Size(max = 100)
    private String price;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRateType() {
        return rateType;
    }

    public String getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
