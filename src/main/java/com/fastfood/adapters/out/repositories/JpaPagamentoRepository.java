package com.fastfood.adapters.out.repositories;

import com.fastfood.adapters.out.entities.JpaPagamentoEntity;
import com.fastfood.adapters.out.entities.JpaPedidoEntity;
import com.fastfood.domain.pagamento.EnumStatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPagamentoRepository extends JpaRepository<JpaPagamentoEntity, UUID> {

        List<JpaPagamentoEntity> findByStatus(EnumStatusPagamento status);

        JpaPagamentoEntity findByPedidoId(UUID id);

        

}
