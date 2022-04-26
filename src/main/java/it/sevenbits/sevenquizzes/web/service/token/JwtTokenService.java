package it.sevenbits.sevenquizzes.web.service.token;

import it.sevenbits.sevenquizzes.core.model.user.User;
import it.sevenbits.sevenquizzes.core.model.user.UserCredentials;

public interface JwtTokenService {
    /**
     * Parses the token
     *
     * @param token the token string to parse
     * @return authenticated data
     */
    UserCredentials parseToken(String token);

    /**
     * Creates new Token for user.
     *
     * @param user contains User to be represented as token
     * @return signed token
     */
    String createToken(User user);
}
