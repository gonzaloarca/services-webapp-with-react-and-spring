package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.validation.MatchingFields;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NewUserDto {

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String username;

    @Pattern(regexp = "^\\+?[0-9- ]{7,50}")
    @NotBlank
    private String phone;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank
    private String webPageUrl;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getWebPageUrl() {
        return webPageUrl;
    }

    public void setWebPageUrl(String webPageUrl) {
        this.webPageUrl = webPageUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
