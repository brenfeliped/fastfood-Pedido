package com.fastfood.application.pedido.integration;

import com.fastfood.application.pedido.integration.dto.ProdutoResponseDTO;
import com.fastfood.domain.exceptions.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoIntegrationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private ProdutoIntegrationService produtoIntegrationService;

    private final String apiUrl = "http://api-url";

    @BeforeEach
    void setUp() {
        // Injeta o mock via construtor
        produtoIntegrationService = new ProdutoIntegrationService(restTemplate);
        ReflectionTestUtils.setField(produtoIntegrationService, "producaoApiUrl", apiUrl);
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        UUID id = UUID.randomUUID();
        ProdutoResponseDTO dto = new ProdutoResponseDTO(id, "Nome", "Desc", BigDecimal.TEN, "CAT", "url");

        when(restTemplate.getForObject(anyString(), eq(ProdutoResponseDTO.class))).thenReturn(dto);

        ProdutoResponseDTO resultado = produtoIntegrationService.buscarProdutoPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        // Simula o lançamento da exceção HttpClientErrorException.NotFound
        when(restTemplate.getForObject(anyString(), eq(ProdutoResponseDTO.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoIntegrationService.buscarProdutoPorId(id));
    }

    @Test
    void deveLancarRuntimeExceptionParaOutrosErros() {
        UUID id = UUID.randomUUID();
        when(restTemplate.getForObject(anyString(), eq(ProdutoResponseDTO.class)))
                .thenThrow(new RuntimeException("Erro de conexão"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> produtoIntegrationService.buscarProdutoPorId(id));
        assertTrue(ex.getMessage().contains("Erro ao buscar produto"));
    }
}
