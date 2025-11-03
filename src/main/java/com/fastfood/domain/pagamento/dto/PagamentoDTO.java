package com.fastfood.domain.pagamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    private UUID id;
    private UUID pedidoId;
}
