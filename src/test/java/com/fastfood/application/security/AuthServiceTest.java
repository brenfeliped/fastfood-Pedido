package com.fastfood.application.security;

import com.fastfood.application.security.dto.AuthRequest;
import com.fastfood.application.security.dto.AuthResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void deveGerarToken() {
        String cpf = "12345678900";
        String tokenEsperado = "token-jwt-mock";
        AuthRequest request = new AuthRequest(cpf);

        when(jwtService.gerarToken(cpf)).thenReturn(tokenEsperado);

        AuthResponse response = authService.gerarToken(request);

        assertNotNull(response);
        assertEquals(tokenEsperado, response.token());
        verify(jwtService).gerarToken(cpf);
    }
}
