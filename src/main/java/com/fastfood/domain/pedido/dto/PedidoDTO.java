package com.fastfood.domain.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoDTO {

    private String cpfCliente;
    private List<ItemPedidoDTO> itemPedidoDTOS;
}
