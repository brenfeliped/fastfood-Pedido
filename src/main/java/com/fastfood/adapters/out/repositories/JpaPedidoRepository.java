package com.fastfood.adapters.out.repositories;

import com.fastfood.adapters.out.entities.JpaPedidoEntity;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaPedidoRepository extends JpaRepository<JpaPedidoEntity, UUID> {
    List<JpaPedidoEntity> findByStatus(EnumStatusPedido status);

    @Query("""
    SELECT p FROM JpaPedidoEntity p
    WHERE p.status <> 'FINALIZADO'
    ORDER BY 
        CASE p.status
            WHEN 'PRONTO' THEN 1
            WHEN 'EM_PREPARACAO' THEN 2
            WHEN 'RECEBIDO' THEN 3
            ELSE 4
        END,
        p.atualizadoEm ASC
""")
    List<JpaPedidoEntity> listarFilaPedidos();

    @Query("SELECT p FROM JpaPedidoEntity p WHERE p.cliente.id = :id")
    List<JpaPedidoEntity> findByClienteId(@Param("id") UUID id);
}
