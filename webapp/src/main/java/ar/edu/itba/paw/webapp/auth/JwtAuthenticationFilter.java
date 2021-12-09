package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.input.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger jwtAuthenticationFilterLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
        LoginDto credentials = null;
        Exception ex;
        try {
            jwtAuthenticationFilterLogger.debug("Trying login...");
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read credentials");
        } catch (BadCredentialsException e) {
            jwtAuthenticationFilterLogger.debug("Bad credentials");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ex = e;
        } catch (DisabledException e) {
            jwtAuthenticationFilterLogger.debug("User not verified");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ex = e;
        }
        try {
            response.getWriter().write("{ \"message\": \"" + ex.getMessage() + "\"");
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        jwtAuthenticationFilterLogger.debug("Successful authentication creating token");
        User user = userService.findByEmail(authResult.getName()).orElseThrow(UserNotFoundException::new);
        String token = jwtUtil.generateAccessToken(user);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    @Autowired
    @Qualifier("authenticationManager")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
