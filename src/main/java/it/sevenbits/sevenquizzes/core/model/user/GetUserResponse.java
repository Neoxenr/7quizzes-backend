package it.sevenbits.sevenquizzes.core.model.user;

import java.util.List;

public class GetUserResponse {
    private final String userId;
    private final String username;
    private final String email;

    private final List<String> roles;

    /**
     * GetUserResponse constructor
     *
     * @param userId - user's id
     * @param username - username
     * @param roles - user's roles
     */
    public GetUserResponse(final String userId, final String username, final String email, final List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    /**
     * Get user id
     *
     * @return String - user's id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get username
     *
     * @return String - username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get email
     *
     * @return String - email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get user's roles
     *
     * @return List<String> user's roles
     */
    public List<String> getRoles() {
        return roles;
    }
}
