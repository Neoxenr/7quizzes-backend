package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.token.JwtToken;
import it.sevenbits.sevenquizzes.web.model.user.SignInRequest;
import it.sevenbits.sevenquizzes.web.service.login.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login controller
 */
@RestController
@RequestMapping("/signin")
public class LoginController {
    private final LoginService loginService;

    /**
     * LoginController constructor
     * @param loginService - login service
     */
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Return response with jwt token
     *
     * @param signInRequest - sign in dto
     * @return ResponseEntity<JwtToken> - response with jwt token
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<JwtToken> login(@RequestBody final SignInRequest signInRequest) {
        return new ResponseEntity<>(loginService.login(signInRequest), HttpStatus.OK);
    }
}
