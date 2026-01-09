package com.fastfood.application.pedido.integration.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoCriadoEventTest {

    @Test
    void deveCriarObjetoCorretamente() {
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;

        PedidoCriadoEvent event = PedidoCriadoEvent.builder()
                .pedidoId(pedidoId)
                .valor(valor)
                .build();

        assertEquals(pedidoId, event.getPedidoId());
        assertEquals(valor, event.getValor());
    }

    @Test
    void deveTestarNoArgsConstructor() {
        PedidoCriadoEvent event = new PedidoCriadoEvent();
        assertNull(event.getPedidoId());
        assertNull(event.getValor());
    }

    @Test
    void deveTestarAllArgsConstructor() {
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        
        PedidoCriadoEvent event = new PedidoCriadoEvent(pedidoId, valor);
        
        assertEquals(pedidoId, event.getPedidoId());
        assertEquals(valor, event.getValor());
    }
}
