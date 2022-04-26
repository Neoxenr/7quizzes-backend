package it.sevenbits.sevenquizzes.core.model.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {
    private final String accessToken;

    /**
     * JwtToken constructor
     *
     * @param accessToken - access token
     */
    @JsonCreator
    public JwtToken(@JsonProperty final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get token
     *
     * @return String - access token
     */
    public String getAccessToken() {
        return accessToken;
    }
}
