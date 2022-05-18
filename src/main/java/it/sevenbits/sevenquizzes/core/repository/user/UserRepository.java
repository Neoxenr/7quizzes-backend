package it.sevenbits.sevenquizzes.core.repository.user;

import it.sevenbits.sevenquizzes.core.model.user.User;
import java.util.List;

public interface UserRepository {
    /**
     * Create user
     *
     * @param email - email
     * @param password - password
     * @return User - created user
     */
    User create(String email, String password);

    /**
     * Get user by id
     *
     * @param userId - user id
     * @return User - received user by id
     */
    User getById(String userId);

    /**
     * Get user by email
     *
     * @param email - email
     * @return User - received user by email
     */
    User getByEmail(String email);

    /**
     * Get all user in repository
     *
     * @return List<User> - all users in repository
     */
    List<User> getAll();
}
