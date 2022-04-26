package it.sevenbits.sevenquizzes.web.service.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class StartGameException extends RuntimeException {
    /**
     * StartGameException constructor
     *
     * @param message - error's message
     */
    public StartGameException(final String message) {
        super(message);
    }

    /**
     * StartGameException constructor
     *
     * @param message - error's message
     * @param cause - previous message
     */
    public StartGameException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
