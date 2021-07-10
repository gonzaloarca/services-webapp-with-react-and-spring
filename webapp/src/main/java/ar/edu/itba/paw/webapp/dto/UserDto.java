package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private long id;
    private String email;
    private String username;
    private String phone;
    private String password;
    private List<UserAuth.Role> roles;

    public static UserDto fromUserAndRoles(User user, List<UserAuth.Role> roles){
        final UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.email = user.getEmail();                
        dto.username = user.getUsername();
        dto.phone = user.getPhone();
        dto.roles = roles;
        return dto;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<UserAuth.Role> getRoles() {
        return roles;
    }

    public void setRoles(List<UserAuth.Role> roles) {
        this.roles = roles;
    }
}
