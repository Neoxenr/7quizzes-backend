package it.sevenbits.sevenquizzes.web.service.registration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationFailedException extends RuntimeException {
    /**
     * RegistrationFailedException constructor
     *
     * @param message - error's message
     */
    public RegistrationFailedException(final String message) {
        super(message);
    }

    /**
     *
     *
     * @param message - error's message
     * @param cause - previous error
     */
    public RegistrationFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
