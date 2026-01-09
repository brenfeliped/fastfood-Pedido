package com.fastfood.application.security.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void deveCriarObjetoCorretamente() {
        String cpf = "12345678900";
        AuthRequest request = new AuthRequest(cpf);
        assertEquals(cpf, request.cpf());
    }
}
