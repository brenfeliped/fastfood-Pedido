package com.fastfood.application.security;

import com.fastfood.application.security.dto.AuthRequest;
import com.fastfood.application.security.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    public AuthResponse gerarToken(AuthRequest request) {
        String token = jwtService.gerarToken(request.cpf());
        return new AuthResponse(token);
    }
}
