package it.sevenbits.sevenquizzes.web.service.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginFailedException extends RuntimeException {
    /**
     * LoginFailedException constructor
     *
     * @param message - error's message
     */
    public LoginFailedException(final String message) {
        super(message);
    }

    /**
     * LoginFailedExceptino constructor
     *
     * @param message - error's message
     * @param cause - previous error
     */
    public LoginFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
