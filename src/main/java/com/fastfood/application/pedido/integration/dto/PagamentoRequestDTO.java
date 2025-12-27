package com.fastfood.application.pedido.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequestDTO {
    private UUID pedidoId;
    private BigDecimal valor;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
