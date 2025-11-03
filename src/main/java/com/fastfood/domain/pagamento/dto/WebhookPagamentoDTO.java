package com.fastfood.domain.pagamento.dto;


import com.fastfood.domain.pagamento.EnumStatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebhookPagamentoDTO {

    private UUID idPagamento;

    private EnumStatusPagamento status;
}
