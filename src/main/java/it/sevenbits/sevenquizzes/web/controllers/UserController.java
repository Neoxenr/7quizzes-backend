package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.user.GetUserResponse;
import it.sevenbits.sevenquizzes.web.security.AuthRoleRequired;
import it.sevenbits.sevenquizzes.web.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User controller
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * UserController constructor
     *
     * @param userService - user service
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @return ResponseEntity<List<GetUserResponse>> - response with list of GetUserResponse dto
     */
    @GetMapping
    @ResponseBody
    @AuthRoleRequired("ADMIN")
    public ResponseEntity<List<GetUserResponse>> getUsers() {
        return new ResponseEntity<>(userService.getUsers().getUsers(), HttpStatus.OK);
    }

    /**
     * Get user
     *
     * @param id - user id
     * @return ResponseEntity<GetUserResponse> - response with GetUserResponse dto
     */
    @GetMapping("/{id}")
    @ResponseBody
    @AuthRoleRequired("ADMIN")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable final String id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
}
