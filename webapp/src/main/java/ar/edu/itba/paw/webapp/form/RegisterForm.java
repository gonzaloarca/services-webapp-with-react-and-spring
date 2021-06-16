package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.MatchingFields;
import ar.edu.itba.paw.webapp.validation.ValidImage;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MatchingFields(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match"
)
public class RegisterForm {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Pattern(regexp = "^\\+?[0-9- ]{7,50}")
    private String phone;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @Size(min = 8, max = 100)
    private String password;

    @ValidImage
    private MultipartFile avatar;

    @NotBlank
    private String repeatPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }
}
