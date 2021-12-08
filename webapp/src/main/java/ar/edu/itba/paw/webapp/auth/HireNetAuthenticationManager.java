package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HireNetAuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Optional<UserAuth> user = userService.getAuthInfo(username);
        System.out.println(username);
        if (!user.isPresent()) {
            throw new BadCredentialsException("Invalid Credentials1");
        }
        if (!encoder.matches(password, user.get().getPassword())) {
            throw new BadCredentialsException("Invalid Credentials2");
        }
        if (!user.get().isVerified()) {
            throw new DisabledException("User is not verified");
        }
        return new UsernamePasswordAuthenticationToken(username, null, user.get().getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList()));
    }
}
