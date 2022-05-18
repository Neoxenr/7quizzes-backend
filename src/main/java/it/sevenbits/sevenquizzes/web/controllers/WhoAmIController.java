package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.user.UserCredentials;
import it.sevenbits.sevenquizzes.web.security.AuthRoleRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whoami")
public class WhoAmIController {
    /**
     * Return response with user's credentials
     *
     * @param userCredentials - user's credentials
     * @return ResponseEntity<UserCredentials> - response with user's credentials
     */
    @GetMapping
    @ResponseBody
    @AuthRoleRequired("USER")
    public ResponseEntity<UserCredentials> get(final UserCredentials userCredentials) {
        return ResponseEntity.ok(userCredentials);
    }
}
