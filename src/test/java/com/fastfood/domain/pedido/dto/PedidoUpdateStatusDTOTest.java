package com.fastfood.domain.pedido.dto;

import com.fastfood.domain.pedido.EnumStatusPedido;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoUpdateStatusDTOTest {

    @Test
    void deveCriarObjetoCorretamente() {
        UUID id = UUID.randomUUID();
        EnumStatusPedido status = EnumStatusPedido.PRONTO;

        PedidoUpdateStatusDTO dto = new PedidoUpdateStatusDTO();
        dto.setId(id);
        dto.setEnumStatusPedido(status);

        assertEquals(id, dto.getId());
        assertEquals(status, dto.getEnumStatusPedido());
    }

    @Test
    void deveTestarSetters() {
        PedidoUpdateStatusDTO dto = new PedidoUpdateStatusDTO();
        UUID id = UUID.randomUUID();

        dto.setId(id);
        dto.setEnumStatusPedido(EnumStatusPedido.FINALIZADO);

        assertEquals(id, dto.getId());
        assertEquals(EnumStatusPedido.FINALIZADO, dto.getEnumStatusPedido());
    }
}
