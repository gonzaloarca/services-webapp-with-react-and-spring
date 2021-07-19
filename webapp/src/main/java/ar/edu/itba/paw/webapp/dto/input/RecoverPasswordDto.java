package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecoverPasswordDto {

    @NotNull
    @Min(0)
    private Long userId;

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
