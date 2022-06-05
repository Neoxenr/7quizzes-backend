package it.sevenbits.sevenquizzes.web.service.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.sevenbits.sevenquizzes.core.model.user.User;
import it.sevenbits.sevenquizzes.core.model.user.UserCredentials;
import it.sevenbits.sevenquizzes.web.security.JwtSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class JsonWebTokenService implements JwtTokenService {
    private final Logger logger = LoggerFactory.getLogger("it.sevenbits.sevenquizzes.web.service.token.logger");

    private final JwtSettings jwtSettings;

    /**
     * JsonWebTokenService constructor
     *
     * @param jwtSettings - jwt settings
     */
    public JsonWebTokenService(final JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public UserCredentials parseToken(final String token) {
        logger.debug("Parsing token {}", token);

        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token);

        final String userId = claims.getBody().getSubject();
        final String email = claims.getBody().get("email", String.class);

        final List<String> userRoles = claims.getBody().get("roles", List.class);

        return new UserCredentials(userId, email, Collections.unmodifiableList(userRoles));
    }

    @Override
    public String createToken(final User user) {
        logger.debug("Generating token for {}", user.getUsername());

        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(jwtSettings.getTokenIssuer())
                .setIssuedAt(Date.from(now))
                .setSubject(user.getUserId())
                .setExpiration(Date.from(now.plus(jwtSettings.getTokenExpiredIn())));

        claims.put("roles", user.getRoles());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();
    }
}
