package com.fastfood.application.security;

import com.fastfood.application.security.dto.ValidacaoResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidacaoTokenService {

    private final JwtService jwtService;

    public ValidacaoResponse validarToken(String token) {
        try {
            Claims claims = jwtService.parseClaims(token);
            
            String username = claims.get("username", String.class);
            if (username == null) {
                username = claims.getSubject();
            }
            
            String tokenUse = claims.get("token_use", String.class);
            Long iat = claims.getIssuedAt().getTime() / 1000;
            Long exp = claims.getExpiration().getTime() / 1000;

            ValidacaoResponse.Claims responseClaims = new ValidacaoResponse.Claims(
                    username,
                    tokenUse,
                    iat,
                    exp
            );

            return new ValidacaoResponse("Token válido", responseClaims);

        } catch (Exception ex) {
            // Token inválido ou expirado
            return null;
        }
    }
}
