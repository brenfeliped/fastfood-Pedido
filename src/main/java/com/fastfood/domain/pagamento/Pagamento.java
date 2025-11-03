package com.fastfood.domain.pagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    private UUID id;
    private UUID pedidoId;
    private EnumStatusPagamento status;


}
