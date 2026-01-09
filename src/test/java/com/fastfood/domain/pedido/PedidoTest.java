package com.fastfood.domain.pedido;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void deveCriarPedidoComConstrutorCompleto() {
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        EnumStatusPedido status = EnumStatusPedido.RECEBIDO;
        BigDecimal total = BigDecimal.TEN;
        int senhaPainel = 1234;
        LocalDateTime atualizadoEm = LocalDateTime.now();
        List<ItemPedido> itens = new ArrayList<>();

        Pedido pedido = new Pedido(id, clienteId, status, total, senhaPainel, atualizadoEm, itens);

        assertEquals(id, pedido.getId());
        assertEquals(clienteId, pedido.getClienteId());
        assertEquals(status, pedido.getStatus());
        assertEquals(total, pedido.getTotal());
        assertEquals(senhaPainel, pedido.getSenhaPainel());
        assertEquals(atualizadoEm, pedido.getAtualizadoEm());
        assertEquals(itens, pedido.getItens());
    }

    @Test
    void deveCriarPedidoComConstrutorSemItens() {
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        EnumStatusPedido status = EnumStatusPedido.RECEBIDO;
        BigDecimal total = BigDecimal.TEN;
        int senhaPainel = 1234;
        LocalDateTime atualizadoEm = LocalDateTime.now();

        Pedido pedido = new Pedido(id, clienteId, status, total, senhaPainel, atualizadoEm);

        assertEquals(id, pedido.getId());
        assertEquals(clienteId, pedido.getClienteId());
        assertEquals(status, pedido.getStatus());
        assertEquals(total, pedido.getTotal());
        assertEquals(senhaPainel, pedido.getSenhaPainel());
        assertEquals(atualizadoEm, pedido.getAtualizadoEm());
        assertNotNull(pedido.getItens()); // Inicializado como ArrayList vazio na declaração
        assertTrue(pedido.getItens().isEmpty());
    }

    @Test
    void deveCriarPedidoComConstrutorVazio() {
        Pedido pedido = new Pedido();
        assertNotNull(pedido);
        assertNotNull(pedido.getItens());
    }

    @Test
    void deveTestarSetters() {
        Pedido pedido = new Pedido();
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();
        List<ItemPedido> itens = new ArrayList<>();

        pedido.setId(id);
        pedido.setClienteId(clienteId);
        pedido.setStatus(EnumStatusPedido.EM_PREPARACAO);
        pedido.setTotal(BigDecimal.valueOf(50));
        pedido.setSenhaPainel(9999);
        pedido.setAtualizadoEm(agora);
        pedido.setItens(itens);

        assertEquals(id, pedido.getId());
        assertEquals(clienteId, pedido.getClienteId());
        assertEquals(EnumStatusPedido.EM_PREPARACAO, pedido.getStatus());
        assertEquals(BigDecimal.valueOf(50), pedido.getTotal());
        assertEquals(9999, pedido.getSenhaPainel());
        assertEquals(agora, pedido.getAtualizadoEm());
        assertEquals(itens, pedido.getItens());
    }

    @Test
    void deveAvancarStatusCorretamente() {
        Pedido pedido = new Pedido();
        pedido.setStatus(EnumStatusPedido.RECEBIDO);

        pedido.avancarStatus();
        assertEquals(EnumStatusPedido.EM_PREPARACAO, pedido.getStatus());

        pedido.avancarStatus();
        assertEquals(EnumStatusPedido.PRONTO, pedido.getStatus());

        pedido.avancarStatus();
        assertEquals(EnumStatusPedido.FINALIZADO, pedido.getStatus());
    }

    @Test
    void deveLancarExcecaoAoAvancarStatusFinalizado() {
        Pedido pedido = new Pedido();
        pedido.setStatus(EnumStatusPedido.FINALIZADO);

        assertThrows(IllegalStateException.class, pedido::avancarStatus);
    }
}
