package com.fastfood.configs.security;

import com.fastfood.application.security.ValidacaoTokenService;
import com.fastfood.application.security.dto.ValidacaoResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {



    private final ValidacaoTokenService validacaoTokenService;



    public JwtAuthenticationFilter(ValidacaoTokenService validacaoTokenService) {
        this.validacaoTokenService = validacaoTokenService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // libere endpoints públicos aqui

        return path.startsWith("/fastfood-pedido/auth/")
                || path.startsWith("/fastfood-pedido/swagger-ui/")
                || path.startsWith("/fastfood-pedido/v3/api-docs/")
                || path.startsWith("/fastfood-pedido/public/")
                || path.startsWith("/fastfood-pedido/api/pedidos/")   // libera pedidos
                || path.startsWith("/fastfood-pedido/api/clientes/"); // libera clientes
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            ValidacaoResponse validacao = validacaoTokenService.validarToken(token);

            if (validacao == null || !validacao.valido()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Se o token for válido, cria autenticação
            var auth = new UsernamePasswordAuthenticationToken(
                    validacao.claims().username(), // principal
                    null,
                    Collections.emptyList()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
