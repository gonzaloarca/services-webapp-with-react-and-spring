package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Locale;

@Component
@Path("/users")
public class UserController {
    private final Logger accountControllerLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpHeaders headers;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response register(@Valid final UserDto userDto) {

        String webpageUrl = uriInfo.getAbsolutePathBuilder().replacePath(null)
                .build().toString();

        User currentUser;
        try {
            accountControllerLogger.debug("Registering user with data: email: {}, password: {}, name: {}, phone: {}",
                    userDto.getEmail(), userDto.getPassword(), userDto.getUsername(), userDto.getPhone());
            currentUser = userService.register(userDto.getEmail(), userDto.getPassword(), userDto.getUsername(), userDto.getPhone(),
                    null, headers.getAcceptableLanguages().get(0), webpageUrl);
        } catch (UserAlreadyExistsException e) {
            accountControllerLogger.error("Register error: email already exists");
            return Response.status(Response.Status.CONFLICT).build();
        }
        final URI userUri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(currentUser.getId()))
                .build();
        return Response.created(userUri).build();
    }

    @Path("/{id}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") long id) {
        accountControllerLogger.debug("Finding user with email {}", id);
        User user = userService.findById(id);
        accountControllerLogger.debug("Finding auth info for user with email {}", user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);

        UserDto userAnswer = UserDto.fromUserAndRoles(user, auth.getRoles());
        return Response.ok(userAnswer).build();
    }

    @Path("/search/")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getByEmail(@QueryParam("email") String email) {
        accountControllerLogger.debug("Finding user with email {}", email);
        User currentUser = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
        accountControllerLogger.debug("Finding auth info for user with email {}", currentUser.getEmail());
        UserAuth auth = userService.getAuthInfo(currentUser.getEmail()).orElseThrow(UserNotFoundException::new);

        UserDto userAnswer = UserDto.fromUserAndRoles(currentUser, auth.getRoles());
        return Response.ok(userAnswer).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changeData(final UserDto userDto,@PathParam("id") final long id) {

        accountControllerLogger.debug("Finding user with id {}", id);
        User user = userService.findById(id);
        accountControllerLogger.debug("Finding auth info for user with email {}", user.getEmail());
        UserAuth auth = userService.getAuthInfo(user.getEmail()).orElseThrow(UserNotFoundException::new);


        String username = userDto.getUsername() != null ? userDto.getUsername() : user.getUsername();
        String phone = userDto.getPhone() != null ? userDto.getPhone() : user.getPhone();
        accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}",id,
                username, phone);
        User updatedUser = userService.updateUserById(id, username, phone);

        UserDto userAnswer = UserDto.fromUserAndRoles(updatedUser, auth.getRoles());
        return Response.ok(userAnswer).build();
    }

    @Path("/{id}/security")
    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changePassword(final UserDto user,@PathParam("id") final long id) {

        accountControllerLogger.debug("Updating user password with id {}", id);

        userService.changeUserPassword(id,user.getPassword());

        return Response.ok(userService.findById(id)).build();
    }
}

