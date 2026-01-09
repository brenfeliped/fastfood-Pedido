package com.fastfood.application.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "minha_chave_secreta_super_segura_123456"; // 32+ chars
    private final long expiration = 3600;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secret);
        ReflectionTestUtils.setField(jwtService, "expirationSeconds", expiration);
    }

    @Test
    void deveGerarTokenValido() {
        String cpf = "12345678900";
        String token = jwtService.gerarToken(cpf);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtService.isTokenValido(token));
    }

    @Test
    void deveValidarClaimsDoToken() {
        String cpf = "12345678900";
        String token = jwtService.gerarToken(cpf);

        Claims claims = jwtService.parseClaims(token);

        assertEquals(cpf, claims.getSubject());
        assertEquals(cpf, claims.get("username"));
        assertEquals("access", claims.get("token_use"));
    }

    @Test
    void deveRetornarFalsoParaTokenInvalido() {
        assertFalse(jwtService.isTokenValido("token-invalido"));
    }
}
