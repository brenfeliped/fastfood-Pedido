package com.fastfood.application.security;


import com.fastfood.application.security.dto.ValidacaoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidacaoTokenService {

    @Value("${external.auth-api.validar-token-url}")
    private String validarTokenUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ValidacaoResponse validarToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    validarTokenUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Se quiser converter para DTO com Jackson:
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(response.getBody(), ValidacaoResponse.class);

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao validar token: " + ex.getMessage(), ex);
        }
    }
}
