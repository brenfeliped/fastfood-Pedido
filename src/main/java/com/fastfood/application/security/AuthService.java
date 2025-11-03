package com.fastfood.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastfood.application.security.dto.AuthRequest;
import com.fastfood.application.security.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${external.auth-api.url}")
    private String authApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthResponse gerarToken(AuthRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AuthRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    authApiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), AuthResponse.class);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao chamar API de autenticação: " + ex.getMessage(), ex);
        }
    }

}
