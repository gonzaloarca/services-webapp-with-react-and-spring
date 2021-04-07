package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContractForm {

    // TODO: verificar size de las variables

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String name;

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "^\\+?[0-9- ]{7,50}")
    private String phone;

    @NotBlank
    @Size(max = 1000)
    private String description;

    //TODO: proximo sprint para imagen
    // private MultipartFile image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/*
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
*/
}
