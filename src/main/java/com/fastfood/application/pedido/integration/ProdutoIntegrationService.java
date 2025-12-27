package com.fastfood.application.pedido.integration;

import com.fastfood.application.pedido.integration.dto.ProdutoResponseDTO;
import com.fastfood.domain.exceptions.ProdutoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ProdutoIntegrationService {

    @Value("${external.producao-api.url}")
    private String producaoApiUrl;

    private final RestTemplate restTemplate;

    public ProdutoIntegrationService() {
        this.restTemplate = new RestTemplate();
    }

    public ProdutoResponseDTO buscarProdutoPorId(UUID id) {
        try {
            String url = producaoApiUrl + "/api/produtos/" + id;
            return restTemplate.getForObject(url, ProdutoResponseDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProdutoNaoEncontradoException(id);
        } catch (Exception e) {
            // Em um cenário real, poderíamos ter um tratamento de erro mais robusto ou circuit breaker
            throw new RuntimeException("Erro ao buscar produto no serviço de produção: " + e.getMessage(), e);
        }
    }
}
