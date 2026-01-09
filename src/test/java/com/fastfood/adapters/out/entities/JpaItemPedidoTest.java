package com.fastfood.adapters.out.entities;

import com.fastfood.domain.pedido.ItemPedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaItemPedidoTest {

    @Test
    void deveCriarAPartirDoDominio() {
        UUID produtoId = UUID.randomUUID();
        // Construtor do ItemPedido: produtoId, nome, descricao, imagemUrl, quantidade, precoUnitario
        ItemPedido item = new ItemPedido(produtoId, "Lanche", "Desc", "url", 2, BigDecimal.TEN);

        // O construtor do JpaItemPedido espera: produtoId, quantidade, precoUnitario
        JpaItemPedido entity = new JpaItemPedido(item.getProdutoId(), item.getQuantidade(), item.getPrecoUnitario());

        assertEquals(produtoId, entity.getProdutoId());
        assertEquals(2, entity.getQuantidade());
        assertEquals(BigDecimal.TEN, entity.getPrecoUnitario());
    }
    
    @Test
    void deveTestarSetters() {
        JpaItemPedido entity = new JpaItemPedido();
        UUID produtoId = UUID.randomUUID();

        entity.setProdutoId(produtoId);
        entity.setQuantidade(5);
        entity.setPrecoUnitario(BigDecimal.ONE);

        assertEquals(produtoId, entity.getProdutoId());
        assertEquals(5, entity.getQuantidade());
        assertEquals(BigDecimal.ONE, entity.getPrecoUnitario());
    }
    
    @Test
    void deveCalcularSubtotal() {
        JpaItemPedido entity = new JpaItemPedido(UUID.randomUUID(), 2, BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(20), entity.calcularSubtotal());
    }
}
