package ar.edu.itba.paw.webapp.restcontrollers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.JwtUtils;
import ar.edu.itba.paw.webapp.dto.input.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@Path("/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserService userService;

    @Path("/login")
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginDto loginData){
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginData.getEmail(), loginData.getPassword()
                            )
                    );

            Optional<User> user = userService.findByEmail(authenticate.getName());
            if(!user.isPresent())
                throw new BadCredentialsException("Invalid credentials");

            return Response.ok().header(HttpHeaders.AUTHORIZATION,jwtUtil.generateAccessToken(user.get())).build();
        } catch (BadCredentialsException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (IOException e){
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }
}
