package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.dto.input.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto credentials = new ObjectMapper().readValue(request.getInputStream(),LoginDto.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),credentials.getPassword()
            ));
        }catch (IOException e){
            throw new RuntimeException("Cannot read credentials");
        }catch (BadCredentialsException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = userService.findByEmail(authResult.getName()).orElseThrow(UserNotFoundException::new);
        String token = jwtUtil.generateAccessToken(user);
        response.setHeader(HttpHeaders.AUTHORIZATION,"Bearer " + token);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    @Autowired
    @Qualifier("authenticationManager")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
