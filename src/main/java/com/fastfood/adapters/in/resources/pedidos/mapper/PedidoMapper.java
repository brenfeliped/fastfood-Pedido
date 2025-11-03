package com.fastfood.adapters.in.resources.pedidos.mapper;

import com.fastfood.adapters.in.resources.pedidos.dto.PedidoResponseDTO;
import com.fastfood.domain.pedido.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponseDTO toDto(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getSenhaPainel(),
                pedido.getAtualizadoEm()
        );
    }

    public static List<PedidoResponseDTO> toDtoList(List<Pedido> pedidos) {
        return pedidos.stream().map(PedidoMapper::toDto).collect(Collectors.toList());
    }
}
