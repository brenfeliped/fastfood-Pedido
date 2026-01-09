package com.fastfood.adapters.in.resources.pedidos.mapper;

import com.fastfood.adapters.in.resources.pedidos.dto.PedidoResponseDTO;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoMapperTest {

    @Test
    void deveMapearParaDto() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());

        PedidoResponseDTO dto = PedidoMapper.toDto(pedido);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(pedido.getClienteId(), dto.getClienteId());
        assertEquals(pedido.getStatus(), dto.getStatus());
        assertEquals(pedido.getTotal(), dto.getTotal());
        assertEquals(pedido.getSenhaPainel(), dto.getSenhaPainel());
        assertEquals(pedido.getAtualizadoEm(), dto.getAtualizadoEm());
    }

    @Test
    void deveMapearListaParaDto() {
        Pedido pedido1 = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        Pedido pedido2 = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.PRONTO, BigDecimal.ONE, 456, LocalDateTime.now());

        List<PedidoResponseDTO> dtos = PedidoMapper.toDtoList(List.of(pedido1, pedido2));

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(pedido1.getId(), dtos.get(0).getId());
        assertEquals(pedido2.getId(), dtos.get(1).getId());
    }
}
