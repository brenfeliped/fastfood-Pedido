package com.fastfood;

import com.fastfood.domain.exceptions.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleProdutoNotFound() {
        UUID id = UUID.randomUUID();
        ProdutoNaoEncontradoException ex = new ProdutoNaoEncontradoException(id);
        ResponseEntity<?> response = handler.handleProdutoNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map body = (Map) response.getBody();
        assertEquals(404, body.get("status"));
        assertEquals("Produto com ID " + id + " não encontrado.", body.get("error"));
    }

    @Test
    void handleEnumParseError() {
        IllegalArgumentException ex = new IllegalArgumentException("Erro");
        ResponseEntity<?> response = handler.handleEnumParseError(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map body = (Map) response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Categoria inválida.", body.get("error"));
    }

    @Test
    void handleGenericError() {
        Exception ex = new Exception("Erro genérico");
        ResponseEntity<?> response = handler.handleGenericError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map body = (Map) response.getBody();
        assertEquals(500, body.get("status"));
        assertEquals("Erro interno no servidor.", body.get("error"));
    }

    @Test
    void handleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "message");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("message", response.getBody().get("field"));
    }
}
