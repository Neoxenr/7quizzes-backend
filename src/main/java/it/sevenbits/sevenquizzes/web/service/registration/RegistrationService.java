package it.sevenbits.sevenquizzes.web.service.registration;

import it.sevenbits.sevenquizzes.core.repository.user.UserRepository;
import it.sevenbits.sevenquizzes.core.security.PasswordEncoder;
import it.sevenbits.sevenquizzes.web.model.user.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * RegistrationService constructor
     *
     * @param userRepository - user repository
     * @param passwordEncoder - password encoder
     */
    public RegistrationService(final UserRepository userRepository,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create user
     *
     * @param signUpRequest - sign up dto
     */
    public void create(final SignUpRequest signUpRequest) {
        final boolean isUserExist = userRepository.getByEmail(signUpRequest.getEmail()) != null;

        if (isUserExist) {
            throw new RegistrationFailedException("The user is already exist");
        }

        final String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword(), 6);

        userRepository.create(signUpRequest.getEmail(), hashedPassword);
    }
}
