package it.sevenbits.sevenquizzes.core.model.user;

import java.util.List;

public class User {
    private final String userId;
    private final String email;
    private final String username;
    private final String password;

    private final List<String> roles;

    /**
     * User constructor
     *
     * @param userId - user id
     * @param email - email
     * @param username - username
     * @param password - password
     * @param roles - roles
     */
    public User(final String userId,
            final String email,
            final String username,
            final String password,
            final List<String> roles) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;

        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
