package com.fastfood.domain.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemPedidoDTO {

    private UUID produtoId;
    private Integer quantidade;
}
