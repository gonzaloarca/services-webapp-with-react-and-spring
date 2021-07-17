package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtUtils {

    @Value("classpath:key.txt")
    private Resource jwtSecret;
    private final String jwtIssuer = "hirenet.com";

    private final Logger JwtUtilsLogger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateAccessToken(User user) throws IOException {
        return Jwts.builder()
                .setSubject(format("%s", user.getEmail()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(SignatureAlgorithm.HS512, StreamUtils.copyToByteArray(jwtSecret.getInputStream()))
                .compact();
    }

    public String getUserId(String token) throws IOException {
        Claims claims = Jwts.parser()
                .setSigningKey(StreamUtils.copyToByteArray(jwtSecret.getInputStream()))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getUsername(String token) throws IOException {
        Claims claims = Jwts.parser()
                .setSigningKey(StreamUtils.copyToByteArray(jwtSecret.getInputStream()))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) throws IOException {
        Claims claims = Jwts.parser()
                .setSigningKey(StreamUtils.copyToByteArray(jwtSecret.getInputStream()))
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) throws IOException {
        try {
            Jwts.parser().setSigningKey(StreamUtils.copyToByteArray(jwtSecret.getInputStream())).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            JwtUtilsLogger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            JwtUtilsLogger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            JwtUtilsLogger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            JwtUtilsLogger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JwtUtilsLogger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
