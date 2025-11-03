package com.fastfood.domain.pagamento.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;

public class PagamentoMapper {

    public static PagamentoDTO toDTO(Pagamento pagamento) {
        if (pagamento == null) return null;

        return new PagamentoDTO(
                pagamento.getId(),
                pagamento.getPedidoId()
        );
    }

    public static Pagamento toDomain(PagamentoDTO dto) {
        if (dto == null) return null;

        return new Pagamento(
                dto.getId(),
                dto.getPedidoId(),
                EnumStatusPagamento.PENDENTE
        );
    }
}
