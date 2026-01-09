package com.fastfood.application.pedido.integration.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoResponseDTOTest {

    @Test
    void deveCriarObjetoCorretamente() {
        UUID id = UUID.randomUUID();
        String nome = "Lanche";
        String descricao = "Desc";
        BigDecimal preco = BigDecimal.TEN;
        String categoria = "LANCHE";
        String imagemUrl = "url";

        ProdutoResponseDTO dto = new ProdutoResponseDTO(id, nome, descricao, preco, categoria, imagemUrl);

        assertEquals(id, dto.getId());
        assertEquals(nome, dto.getNome());
        assertEquals(descricao, dto.getDescricao());
        assertEquals(preco, dto.getPreco());
        assertEquals(categoria, dto.getCategoria());
        assertEquals(imagemUrl, dto.getImagemUrl());
    }

    @Test
    void deveTestarSetters() {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        UUID id = UUID.randomUUID();

        dto.setId(id);
        dto.setNome("Teste");
        dto.setDescricao("Desc Teste");
        dto.setPreco(BigDecimal.ONE);
        dto.setCategoria("BEBIDA");
        dto.setImagemUrl("img");

        assertEquals(id, dto.getId());
        assertEquals("Teste", dto.getNome());
        assertEquals("Desc Teste", dto.getDescricao());
        assertEquals(BigDecimal.ONE, dto.getPreco());
        assertEquals("BEBIDA", dto.getCategoria());
        assertEquals("img", dto.getImagemUrl());
    }
}
