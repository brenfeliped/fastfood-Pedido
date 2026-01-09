package com.fastfood.adapters.in.resources.pedidos.mapper;

import com.fastfood.adapters.in.resources.pedidos.dto.PedidoResponseDTO;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PedidoMapperTest {

    @Test
    void deveConverterParaDto() {
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();
        
        Pedido pedido = new Pedido(id, clienteId, EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, agora);

        PedidoResponseDTO dto = PedidoMapper.toDto(pedido);

        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(clienteId, dto.getClienteId());
        assertEquals(EnumStatusPedido.RECEBIDO, dto.getStatus());
        assertEquals(BigDecimal.TEN, dto.getTotal());
        assertEquals(123, dto.getSenhaPainel());
        assertEquals(agora, dto.getAtualizadoEm());
    }

    @Test
    void deveConverterListaParaDto() {
        Pedido pedido = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        List<Pedido> pedidos = List.of(pedido);

        List<PedidoResponseDTO> dtos = PedidoMapper.toDtoList(pedidos);

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(pedido.getId(), dtos.get(0).getId());
    }
}
