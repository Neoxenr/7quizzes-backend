package it.sevenbits.sevenquizzes.core.security;

public interface PasswordEncoder {

    /**
     * Checks the entered password matches with the hashed password
     *
     * @param plainPassword the entered plain text password
     * @param hashedPassword the stored hashed password
     * @return true if the password matches with the hash
     */
    boolean matches(String plainPassword, String hashedPassword);

    /**
     * Return hashed password
     *
     * @param password - password
     * @param saltRounds - salt round
     * @return - hashed password
     */
    String encode(String password, int saltRounds);
}
