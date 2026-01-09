package com.fastfood.application.security.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoResponseTest {

    @Test
    void deveCriarObjetoCorretamente() {
        String message = "Token válido";
        ValidacaoResponse.Claims claims = new ValidacaoResponse.Claims("user", "access", 100L, 200L);
        
        ValidacaoResponse response = new ValidacaoResponse(message, claims);
        
        assertEquals(message, response.message());
        assertEquals(claims, response.claims());
        assertEquals("user", response.claims().username());
        assertEquals("access", response.claims().tokenUse());
        assertEquals(100L, response.claims().iat());
        assertEquals(200L, response.claims().exp());
    }

    @Test
    void deveValidarTokenCorretamente() {
        ValidacaoResponse.Claims claimsValidos = new ValidacaoResponse.Claims("user", "access", 100L, 200L);
        ValidacaoResponse responseValida = new ValidacaoResponse("OK", claimsValidos);
        assertTrue(responseValida.valido());

        ValidacaoResponse.Claims claimsInvalidos = new ValidacaoResponse.Claims(null, null, 100L, 200L);
        ValidacaoResponse responseInvalida = new ValidacaoResponse("Erro", claimsInvalidos);
        assertFalse(responseInvalida.valido());
    }
}
