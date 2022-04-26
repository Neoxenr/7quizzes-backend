package it.sevenbits.sevenquizzes.core.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserCredentials {
    private final String userId;
    private final String email;

    private final List<String> roles;

    /**
     * UserCredentials constructor
     *
     * @param userId - user id
     * @param email - email
     * @param roles - roles
     */
    @JsonCreator
    public UserCredentials(
            @JsonProperty final String userId,
            @JsonProperty final String email,
            @JsonProperty final List<String> roles) {
        this.userId = userId;
        this.email = email;

        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
