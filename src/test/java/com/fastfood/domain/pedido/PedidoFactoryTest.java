package com.fastfood.domain.pedido;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoFactoryTest {

    @Test
    void deveCriarNovoPedidoComTotalCorreto() {
        UUID clienteId = UUID.randomUUID();
        
        ItemPedido item1 = new ItemPedido(UUID.randomUUID(), "Lanche", "Desc", "url", 2, BigDecimal.valueOf(20.0));
        ItemPedido item2 = new ItemPedido(UUID.randomUUID(), "Refri", "Desc", "url", 1, BigDecimal.valueOf(10.0));
        
        List<ItemPedido> itens = List.of(item1, item2);

        Pedido pedido = PedidoFactory.criarNovo(clienteId, itens);

        assertNotNull(pedido);
        assertEquals(clienteId, pedido.getClienteId());
        assertEquals(EnumStatusPedido.RECEBIDO, pedido.getStatus());
        assertEquals(BigDecimal.valueOf(50.0), pedido.getTotal()); // (2*20) + (1*10) = 50
        assertNotNull(pedido.getAtualizadoEm());
        assertTrue(pedido.getSenhaPainel() >= 1000 && pedido.getSenhaPainel() <= 9999);
        assertEquals(itens, pedido.getItens());
    }

    @Test
    void deveTratarPrecoNuloComoZero() {
        UUID clienteId = UUID.randomUUID();
        
        // Item com preço nulo
        ItemPedido item = new ItemPedido(UUID.randomUUID(), "Brinde", "Desc", "url", 1, null);
        
        List<ItemPedido> itens = List.of(item);

        Pedido pedido = PedidoFactory.criarNovo(clienteId, itens);

        assertNotNull(pedido);
        assertEquals(BigDecimal.ZERO, pedido.getTotal());
    }

    @Test
    void deveGerarSenhaPainelAleatoria() {
        UUID clienteId = UUID.randomUUID();
        List<ItemPedido> itens = List.of();

        Pedido pedido1 = PedidoFactory.criarNovo(clienteId, itens);
        Pedido pedido2 = PedidoFactory.criarNovo(clienteId, itens);

        // É possível que sejam iguais, mas improvável. O teste foca em garantir que o valor é gerado.
        assertTrue(pedido1.getSenhaPainel() >= 1000);
        assertTrue(pedido2.getSenhaPainel() >= 1000);
    }
}
