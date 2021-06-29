package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private long id;
    private String email;
    private String username;
    private List<UserAuth.Role> roles;
    public static UserDto fromUser(User user){
        final UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.email = user.getEmail();                
        dto.username = user.getUsername();
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
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
