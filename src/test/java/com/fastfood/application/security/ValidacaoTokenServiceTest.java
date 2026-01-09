package com.fastfood.application.security;

import com.fastfood.application.security.dto.ValidacaoResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTokenServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ValidacaoTokenService validacaoTokenService;

    @Test
    void deveValidarTokenComSucesso() {
        String token = "token-valido";
        String username = "12345678900";
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);

        Claims claims = new DefaultClaims(Map.of(
                "username", username,
                "token_use", "access",
                "iat", now.getTime() / 1000,
                "exp", exp.getTime() / 1000
        ));

        when(jwtService.parseClaims(token)).thenReturn(claims);

        ValidacaoResponse response = validacaoTokenService.validarToken(token);

        assertNotNull(response);
        assertEquals("Token válido", response.message());
        assertEquals(username, response.claims().username());
        assertEquals("access", response.claims().tokenUse());
    }

    @Test
    void deveValidarTokenUsandoSubjectComoUsername() {
        String token = "token-valido";
        String subject = "12345678900";
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);

        // Claims sem "username", forçando o uso do subject
        Claims claims = new DefaultClaims();
        claims.setSubject(subject);
        claims.put("token_use", "access");
        claims.setIssuedAt(now);
        claims.setExpiration(exp);

        when(jwtService.parseClaims(token)).thenReturn(claims);

        ValidacaoResponse response = validacaoTokenService.validarToken(token);

        assertNotNull(response);
        assertEquals(subject, response.claims().username());
    }

    @Test
    void deveRetornarNullParaTokenInvalido() {
        String token = "token-invalido";
        when(jwtService.parseClaims(token)).thenThrow(new RuntimeException("Token inválido"));

        ValidacaoResponse response = validacaoTokenService.validarToken(token);

        assertNull(response);
    }
}
