package com.fastfood.domain.pedido;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    @Test
    void deveCriarItemPedidoCorretamente() {
        UUID produtoId = UUID.randomUUID();
        String nome = "X-Burger";
        String descricao = "Com queijo";
        String imagemUrl = "http://img.com/xburger.jpg";
        int quantidade = 2;
        BigDecimal precoUnitario = BigDecimal.valueOf(15.50);

        ItemPedido item = new ItemPedido(produtoId, nome, descricao, imagemUrl, quantidade, precoUnitario);

        assertEquals(produtoId, item.getProdutoId());
        assertEquals(nome, item.getNome());
        assertEquals(descricao, item.getDescricao());
        assertEquals(imagemUrl, item.getImagemUrl());
        assertEquals(quantidade, item.getQuantidade());
        assertEquals(precoUnitario, item.getPrecoUnitario());
    }
}
