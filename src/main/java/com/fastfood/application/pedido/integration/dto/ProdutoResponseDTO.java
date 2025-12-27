package com.fastfood.application.pedido.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private String imagemUrl;
}
