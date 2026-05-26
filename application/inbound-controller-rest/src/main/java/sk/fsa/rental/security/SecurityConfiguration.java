package sk.fsa.rental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    private final RestSecurityExceptionHandler restSecurityExceptionHandler;

    SecurityConfiguration(RestSecurityExceptionHandler restSecurityExceptionHandler) {
        this.restSecurityExceptionHandler = restSecurityExceptionHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/actuator/health", "/actuator/health/**", "/actuator/prometheus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/listings/featured").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/photos/*/cover-content").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/listings").hasRole("OWNER")
                        .requestMatchers(HttpMethod.GET, "/api/listings/my").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/listings/*").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/listings/*").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/listings/*/photos").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/listings/*/activate").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/listings/*/deactivate").hasRole("OWNER")
                        .requestMatchers(HttpMethod.GET, "/api/owner/stats").hasRole("OWNER")
                        .requestMatchers(HttpMethod.GET, "/api/viewings/owner").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/viewings/*/approve").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/viewings/*/reject").hasRole("OWNER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restSecurityExceptionHandler)
                        .accessDeniedHandler(restSecurityExceptionHandler)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationEntryPoint(restSecurityExceptionHandler)
                        .jwt(jwt -> {
                            jwt.decoder(jwtDecoder);
                            jwt.jwtAuthenticationConverter(JwtConverter::new);
                        })
                )
                .build();
    }
}
