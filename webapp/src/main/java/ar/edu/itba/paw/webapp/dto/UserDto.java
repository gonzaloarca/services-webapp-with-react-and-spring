package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.UserWithImage;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class UserDto {

    private long id;
    private URI uri;

//    @NotBlank
//    @Size(max = 100)
//    @Email
    private String email;

//    @NotBlank
//    @Size(max = 100)
    private String username;

//    @Pattern(regexp = "^\\+?[0-9- ]{7,50}")
    private String phone;

    //TODO FIX NOT NULL
    private String password;

    //TODO FIX NOT NULL
    private List<UserAuth.Role> roles;

    private LinkDto image;

    public static UserDto fromUser(UserWithImage user, UriInfo uriInfo){
        final UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.username = user.getUsername();
        dto.phone = user.getPhone();
        dto.image = user.getByteImage() != null ? new LinkDto(uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(dto.id)).path("/image").build()) : null;
        return dto;
    }

    public static UserDto fromUserAndRoles(UserWithImage user, List<UserAuth.Role> roles, UriInfo uriInfo){
        final UserDto dto = fromUser(user,uriInfo);
        dto.roles = roles;
        return dto;
    }

    public static UserDto linkDataFromUser(User user, UriInfo uriInfo){
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.uri = uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(user.getId())).build();
        return userDto;
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

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public LinkDto getImage() {
        return image;
    }

    public void setImage(LinkDto image) {
        this.image = image;
    }
}
