package it.sevenbits.sevenquizzes.core.repository.user;

import it.sevenbits.sevenquizzes.core.model.user.User;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresUserRepository implements UserRepository {
    private final JdbcOperations jdbcOperations;

    /**
     * PostgresUserRepository constructor
     *
     * @param jdbcOperations - jdbc operations
     */
    public PostgresUserRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /**
     * Create user
     *
     * @param email - email
     * @param password - password
     * @return User - created user
     */
    @Override
    public User create(final String email, final String password) {
        final String userId = UUID.randomUUID().toString();
        final String userRole = "USER";

        final List<String> roles = new ArrayList<>();

        roles.add(userRole);

        jdbcOperations.update(
                "INSERT INTO \"user\" (id, email, username, password) VALUES (?, ?, ?, ?)",
                userId, email, email.substring(0, email.indexOf('@')), password
        );

        jdbcOperations.update(
                "INSERT INTO role (id, name) VALUES (?, ?)",
                userId, userRole
        );

        return new User(userId, email, email.substring(0, email.indexOf('@')), password, roles);
    }

    /**
     * Get user by id
     *
     * @param userId - user id
     * @return User - received user by id
     */
    @Override
    public User getById(final String userId) {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT username FROM \"user\" WHERE id = ?",
                    (resultSet, k) -> {
                        final String username = resultSet.getString("username");
                        final String email = resultSet.getString("email");
                        final String password = resultSet.getString("password");

                        final List<String> userRoles = jdbcOperations.query(
                                "SELECT name FROM role WHERE id = ?",
                                (resultSetRoles, i) -> resultSetRoles.getString("name"),
                                userId
                        );

                        return new User(userId, email, username, password, userRoles);
                    }
            );
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Get user by email
     *
     * @param email - email
     * @return User - received user by email
     */
    @Override
    public User getByEmail(final String email) {
        try {
            return jdbcOperations.queryForObject(
                    "SELECT id, username, password FROM \"user\" WHERE email = ?",
                    (resultSet, k) -> {
                        final String userId = resultSet.getString("id");
                        final String username = resultSet.getString("username");
                        final String password = resultSet.getString("password");

                        final List<String> userRoles = jdbcOperations.query(
                                "SELECT name FROM role WHERE id = ?",
                                (resultSetRoles, i) -> resultSetRoles.getString("name"),
                                userId
                        );

                        return new User(userId, email, username, password, userRoles);
                    },
                    email
            );
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Get all users in repository
     *
     * @return List<User> - all users in repository
     */
    @Override
    public List<User> getAll() {
        return jdbcOperations.query(
                "SELECT * FROM \"user\"",
                (resultSet, k) -> {
                    final String userId = resultSet.getString("id");
                    final String email = resultSet.getString("email");
                    final String username = resultSet.getString("username");
                    final String password = resultSet.getString("password");

                    final List<String> userRoles = jdbcOperations.query(
                            "SELECT name FROM role WHERE id = ?",
                            (resultSetRoles, i) -> resultSetRoles.getString("name"),
                            userId
                    );

                    return new User(userId, email, username, password, userRoles);
                }
        );
    }
}
