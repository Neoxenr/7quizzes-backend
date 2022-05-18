package it.sevenbits.sevenquizzes.web.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpRequest {
    private final String email;

    private final String password;

    /**
     * SignUpRequest constructor
     *
     * @param email - email
     * @param password - password
     */
    @JsonCreator
    public SignUpRequest(@JsonProperty("email") final String email, @JsonProperty("password") final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
