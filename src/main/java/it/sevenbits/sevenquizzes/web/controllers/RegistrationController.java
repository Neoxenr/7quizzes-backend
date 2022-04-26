package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.web.model.user.SignUpRequest;
import it.sevenbits.sevenquizzes.web.service.registration.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class RegistrationController {
    private final RegistrationService registrationService;

    /**
     * RegistrationController constuctor
     *
     * @param registrationService - registration service
     */
    public RegistrationController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Create user
     *
     * @param signUpRequest - sign up dto
     * @return ResponseEntity<?> - HTTP status
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody final SignUpRequest signUpRequest) {
        registrationService.create(signUpRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
