package com.fastfood.configs.security;

import com.fastfood.application.security.ValidacaoTokenService;
import com.fastfood.application.security.dto.ValidacaoResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private ValidacaoTokenService validacaoTokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(validacaoTokenService);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldNotFilterPublicEndpoints() {
        when(request.getRequestURI()).thenReturn("/fastfood-pedido/auth/login");
        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void shouldFilterProtectedEndpoints() {
        when(request.getRequestURI()).thenReturn("/fastfood-pedido/protected");
        assertFalse(filter.shouldNotFilter(request));
    }

    @Test
    void doFilterInternal_NoHeader_ShouldContinueChain() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_InvalidHeader_ShouldContinueChain() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_ValidToken_ShouldAuthenticate() throws ServletException, IOException {
        String token = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        
        ValidacaoResponse.Claims claims = new ValidacaoResponse.Claims("user", "access", 1L, 2L);
        ValidacaoResponse validacaoResponse = new ValidacaoResponse("OK", claims);
        
        when(validacaoTokenService.validarToken(token)).thenReturn(validacaoResponse);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void doFilterInternal_InvalidToken_ShouldReturnUnauthorized() throws ServletException, IOException {
        String token = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(validacaoTokenService.validarToken(token)).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_Exception_ShouldReturnUnauthorized() throws ServletException, IOException {
        String token = "errorToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(validacaoTokenService.validarToken(token)).thenThrow(new RuntimeException("Error"));

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
