package com.fastfood.domain.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemPedidoDTO {

    private UUID produtoId;
    private Integer quantidade;
    // Adicionando campos que podem ser necessários já que Produto não está mais local
    // Mas o DTO de entrada geralmente só tem ID e quantidade.
    // O serviço terá que buscar os detalhes do produto em outro lugar se necessário.
}
