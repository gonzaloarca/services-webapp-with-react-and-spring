package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.ValidImage;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

public class ContractForm {

    @NotBlank
    @Size(max = 100)
    private String description;

    @ValidImage
    //@NotEmptyFile
    private MultipartFile image;

    @DateTimeFormat
    private String scheduledDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
