package it.sevenbits.sevenquizzes.config;

import it.sevenbits.sevenquizzes.web.security.JwtAuthInterceptor;
import it.sevenbits.sevenquizzes.web.security.UserCredentialsResolver;
import it.sevenbits.sevenquizzes.web.service.token.JwtTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final JwtTokenService jwtTokenService;

    /**
     * WebConfiguration constructor
     *
     * @param jwtTokenService - jwt token service
     */
    public WebConfiguration(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    /**
     * Add argument resolvers
     *
     * @param argumentResolvers - argument resolvers
     */
    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserCredentialsResolver());
    }

    /**
     * Add interceptors
     *
     * @param registry - registry
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(
                new JwtAuthInterceptor(jwtTokenService)
        );
    }
}
