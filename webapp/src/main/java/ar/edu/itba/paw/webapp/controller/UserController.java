package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;
import ar.edu.itba.paw.models.exceptions.UserAlreadyExistsException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotVerifiedException;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Locale;

@Component
@Path("/user")
public class UserController {
    static long maxUsernameLength = 100;// FIXME: MAGIC NUMBER, SE USA EN TODOS LOS CONTROLLERS
    private final Logger accountControllerLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    private static boolean isPasswordFormatInvalid(final String password) {
        long passwordLength = password.length();
        return passwordLength < 8 || passwordLength > 50;
    }

    private static boolean isUserDataInvalid(final UserDto userDto) {
        return userDto.getUsername() == null || userDto.getEmail() == null || userDto.getPhone() == null ||
                userDto.getUsername().length() > maxUsernameLength ||
                !userDto.getPhone().matches("^\\+?[0-9- ]{7,50}");
    }

    @Path("/register")
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response register(final UserDto userDto) {
        if (isUserDataInvalid(userDto) || isPasswordFormatInvalid(userDto.getPassword())) {
            //TODO: SE DEBERIA CHEQUEAR EN EL CONTROLLER? O SE DERIVA A SERVICE Y FUE?
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            accountControllerLogger.debug("Registering user with data: email: {}, password: {}, name: {}, phone: {}",
                    userDto.getEmail(), userDto.getPassword(), userDto.getUsername(), userDto.getPhone());
            userService.register(userDto.getEmail(), userDto.getPassword(), userDto.getUsername(), userDto.getPhone(),
                    null, Locale.ENGLISH //FIXME: DE DONDE LO SACO SI NO LO RECIBO POR PARAMETRO? DE @CONTEXT?
            );
        } catch (UserAlreadyExistsException e) {
            accountControllerLogger.error("Register error: email already exists");
            //TODO: Conflict esta bien?
            return Response.status(Response.Status.CONFLICT).build();
        } catch (UserNotVerifiedException e) {
            accountControllerLogger.error("Register error: user not verified");
            return Response.status(Response.Status.CONFLICT).build();
        }

        accountControllerLogger.debug("Finding user with email {}", userDto.getEmail());
        User currentUser = userService.findByEmail(userDto.getEmail()).orElseThrow(UserNotFoundException::new);

        final URI userUri = uriInfo.getBaseUriBuilder()
                .path(String.join("user/id/", String.valueOf(currentUser.getId())))
                .build(); //FIXME: DEVUELVE MAL LA URI, CHEQUEAR LO QUE SE DIO EN CLASE
        return Response.created(userUri).build();
    }

    @Path("/id/{id}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") String id) {
        accountControllerLogger.debug("Finding user with email {}", id);
        User currentUser = userService.findById(Integer.parseInt(id));
        accountControllerLogger.debug("Finding auth info for user with email {}", currentUser.getEmail());
        UserAuth auth = userService.getAuthInfo(currentUser.getEmail()).orElseThrow(UserNotFoundException::new);

        UserDto userAnswer = UserDto.fromUserAndRoles(currentUser, auth.getRoles());
        return Response.ok(userAnswer).build();
    }

    @Path("/email/{email}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getByEmail(@PathParam("email") String email) {
        accountControllerLogger.debug("Finding user with email {}", email);
        User currentUser = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
        accountControllerLogger.debug("Finding auth info for user with email {}", currentUser.getEmail());
        UserAuth auth = userService.getAuthInfo(currentUser.getEmail()).orElseThrow(UserNotFoundException::new);

        UserDto userAnswer = UserDto.fromUserAndRoles(currentUser, auth.getRoles());
        return Response.ok(userAnswer).build();
    }

    @Path("/change_data")
    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changeData(final UserDto userDto) {
        if (isUserDataInvalid(userDto)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        accountControllerLogger.debug("Finding user with email {}", userDto.getEmail());
        User currentUser = userService.findByEmail(userDto.getEmail()).orElseThrow(UserNotFoundException::new);
        accountControllerLogger.debug("Finding auth info for user with email {}", currentUser.getEmail());
        UserAuth auth = userService.getAuthInfo(currentUser.getEmail()).orElseThrow(UserNotFoundException::new);

        accountControllerLogger.debug("Updating user {} with data: name: {}, phone: {}", currentUser.getId(),
                userDto.getUsername(), userDto.getPhone());
        User updatedUser = userService.updateUserById(currentUser.getId(), userDto.getUsername(), userDto.getPhone());

        // TODO: Definir si devolvemos ACCEPTED, OK, o el userDto
        UserDto userAnswer = UserDto.fromUserAndRoles(updatedUser, auth.getRoles());
        return Response.ok(userAnswer).build();
    }

    @Path("/change_password")
    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response changePassword(final UserDto user) {
        if (isPasswordFormatInvalid(user.getPassword())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        accountControllerLogger.debug("Finding user with email {}", user.getEmail());
        User currentUser = userService.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);

        accountControllerLogger.debug("Updating password for user {} ", currentUser.getId());
        userService.changeUserPassword(currentUser.getEmail(), user.getPassword());

        // TODO: Definir si devolvemos ACCEPTED, OK, o el userDto
        return Response.accepted().build();
    }
}

