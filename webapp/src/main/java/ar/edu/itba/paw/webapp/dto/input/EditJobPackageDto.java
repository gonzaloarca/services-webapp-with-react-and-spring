package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.validation.PriceNotEmpty;

import javax.validation.constraints.Size;

@PriceNotEmpty(
        price = "price",
        rateType = "rateType",
        message = "Invalid price"
)
public class EditJobPackageDto {
    @Size(max = 100)
    private String title;

    @Size(max = 100)
    private String description;

    private Integer rateType;

    // Si fuera double y dejamos el campo vacío, tira una excepcion en el lugar del mensaje de error en la pagina
    @Size(max = 9)
    private String price;

    private String isActive;

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String active) {
        isActive = active;
    }
}
