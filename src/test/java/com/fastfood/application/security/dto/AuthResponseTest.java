package com.fastfood.application.security.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void deveCriarObjetoCorretamente() {
        String token = "token-jwt";
        AuthResponse response = new AuthResponse(token);
        assertEquals(token, response.token());
    }
}
