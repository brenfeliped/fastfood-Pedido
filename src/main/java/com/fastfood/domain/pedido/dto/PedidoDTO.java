package com.fastfood.domain.pedido.dto;

import com.fastfood.domain.pedido.EnumStatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoDTO {

    private String cpfCliente;
    private List<ItemPedidoDTO> itemPedidoDTOS;
}
