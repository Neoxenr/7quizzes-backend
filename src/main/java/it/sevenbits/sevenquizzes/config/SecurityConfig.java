package it.sevenbits.sevenquizzes.config;

import it.sevenbits.sevenquizzes.core.security.BCryptPasswordEncoder;
import it.sevenbits.sevenquizzes.core.security.PasswordEncoder;
import it.sevenbits.sevenquizzes.web.security.JwtSettings;
import it.sevenbits.sevenquizzes.web.service.token.JsonWebTokenService;
import it.sevenbits.sevenquizzes.web.service.token.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    /**
     * Get password encoder
     *
     * @return PasswordEncoder - password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Get jwt token service
     *
     * @param settings - jwt token settings
     * @return JwtTokenService - jwt token service
     */
    @Bean
    public JwtTokenService jwtTokenService(final JwtSettings settings) {
        return new JsonWebTokenService(settings);
    }
}
