package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.validation.PriceNotEmpty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PriceNotEmpty(
        price = "price",
        rateType = "rateType",
        message = "Invalid price"
)
public class NewJobPackageDto {
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotNull
    private Integer rateType;

    // Si fuera double y dejamos el campo vacío, tira una excepcion en el lugar del mensaje de error en la pagina
    @Size(max = 9)
    private String price;

    private boolean isActive;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRateType() {
        return rateType;
    }

    public void setRateType(Integer rateType) {
        this.rateType = rateType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
