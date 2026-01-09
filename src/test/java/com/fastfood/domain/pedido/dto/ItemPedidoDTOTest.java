package com.fastfood.domain.pedido.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoDTOTest {

    @Test
    void deveCriarObjetoCorretamente() {
        UUID produtoId = UUID.randomUUID();
        int quantidade = 5;

        ItemPedidoDTO dto = new ItemPedidoDTO(produtoId, quantidade);

        assertEquals(produtoId, dto.getProdutoId());
        assertEquals(quantidade, dto.getQuantidade());
    }

    @Test
    void deveTestarSetters() {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        UUID produtoId = UUID.randomUUID();

        dto.setProdutoId(produtoId);
        dto.setQuantidade(10);

        assertEquals(produtoId, dto.getProdutoId());
        assertEquals(10, dto.getQuantidade());
    }
}
