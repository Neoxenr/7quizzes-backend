package it.sevenbits.sevenquizzes.core.security;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {
    /**
     * Checks the entered password matches with the hashed password
     *
     * @param plainPassword the entered plain text password
     * @param hashedPassword the stored hashed password
     * @return - true if the password matches with the hash
     */
    public boolean matches(final String plainPassword, final String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Return hashed password
     *
     * @param password - password
     * @param saltRounds - salt round
     * @return - hashed password
     */
    @Override
    public String encode(final String password, final int saltRounds) {
        return BCrypt.hashpw(password, BCrypt.gensalt(saltRounds));
    }
}
