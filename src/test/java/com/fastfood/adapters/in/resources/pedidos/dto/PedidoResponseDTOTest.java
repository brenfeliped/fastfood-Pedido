package com.fastfood.adapters.in.resources.pedidos.dto;

import com.fastfood.domain.pedido.EnumStatusPedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoResponseDTOTest {

    @Test
    void deveCriarObjetoCorretamente() {
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        EnumStatusPedido status = EnumStatusPedido.RECEBIDO;
        BigDecimal total = BigDecimal.TEN;
        int senhaPainel = 123;
        LocalDateTime atualizadoEm = LocalDateTime.now();

        PedidoResponseDTO dto = new PedidoResponseDTO(id, clienteId, status, total, senhaPainel, atualizadoEm);

        assertEquals(id, dto.getId());
        assertEquals(clienteId, dto.getClienteId());
        assertEquals(status, dto.getStatus());
        assertEquals(total, dto.getTotal());
        assertEquals(senhaPainel, dto.getSenhaPainel());
        assertEquals(atualizadoEm, dto.getAtualizadoEm());
    }
}
