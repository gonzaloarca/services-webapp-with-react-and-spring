package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.UserWithImage;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class UserDto {

    private long id;
    private String email;
    private String username;
    private String phone;
    private List<UserAuth.Role> roles;
    private URI image;
    private URI contracts;

    public static UserDto fromUser(UserWithImage user, UriInfo uriInfo) {
        final UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.username = user.getUsername();
        dto.phone = user.getPhone();
        dto.image = user.getByteImage() != null ? uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(dto.id)).path("/image").build() : null;
        dto.contracts = uriInfo.getBaseUriBuilder().path("/contracts?userId=" + dto.id).build();
        return dto;
    }

    public static UserDto fromUserAndRoles(UserWithImage user, List<UserAuth.Role> roles, UriInfo uriInfo) {
        final UserDto dto = fromUser(user, uriInfo);
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

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getContracts() {
        return contracts;
    }

    public void setContracts(URI contracts) {
        this.contracts = contracts;
    }
}
