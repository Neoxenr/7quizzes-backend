package it.sevenbits.sevenquizzes.web.service.login;

import it.sevenbits.sevenquizzes.core.model.token.JwtToken;
import it.sevenbits.sevenquizzes.core.model.user.User;
import it.sevenbits.sevenquizzes.core.repository.user.UserRepository;
import it.sevenbits.sevenquizzes.core.security.PasswordEncoder;
import it.sevenbits.sevenquizzes.web.model.user.SignInRequest;
import it.sevenbits.sevenquizzes.web.service.token.JwtTokenService;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    /**
     * LoginService constructor
     *
     * @param userRepository - user repository
     * @param jwtTokenService - jwt token service
     * @param passwordEncoder - password encoder
     */
    public LoginService(
            final UserRepository userRepository,
            final JwtTokenService jwtTokenService,
            final PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Login in quiz
     *
     * @param signInRequest - sign in dto
     * @return JwtToken - user's token
     */
    public JwtToken login(final SignInRequest signInRequest) {
        final User user = userRepository.getByEmail(signInRequest.getEmail());

        if (user == null) {
            throw new LoginFailedException("User with email " + signInRequest.getEmail() + " not found");
        }

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }

        return new JwtToken(jwtTokenService.createToken(user));
    }
}
