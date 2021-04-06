package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.PriceNotEmpty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@PriceNotEmpty
public class PackageForm {
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private int rateType;

    // Si fuera double y dejamos el campo vacío, tira una excepcion en el lugar del mensaje de error en la pagina
    private String price;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRateType() {
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

    public void setRateType(int rateType) {
        this.rateType = rateType;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
