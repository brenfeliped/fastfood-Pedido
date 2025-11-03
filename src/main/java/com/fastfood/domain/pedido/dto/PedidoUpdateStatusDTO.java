package com.fastfood.domain.pedido.dto;

import com.fastfood.domain.pedido.EnumStatusPedido;
import lombok.Data;

import java.util.UUID;

@Data
public class PedidoUpdateStatusDTO {

    private UUID id;

    private EnumStatusPedido enumStatusPedido;
}
