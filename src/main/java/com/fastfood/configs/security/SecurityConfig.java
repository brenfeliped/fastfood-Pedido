package com.fastfood.configs.security;

import com.fastfood.application.security.JwtService;
import com.fastfood.application.security.ValidacaoTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final ValidacaoTokenService validacaoTokenService;

    public SecurityConfig(ValidacaoTokenService validacaoTokenService) {

        this.validacaoTokenService = validacaoTokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtValidationFilter = new JwtAuthenticationFilter(validacaoTokenService);

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // endpoints públicos
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/public/**", "api/clientes/**").permitAll()
                        // qualquer outro precisa de autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
