package com.fastfood.domain.pedido;

import com.fastfood.adapters.out.entities.JpaClienteEntity;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository {

    Pedido salvar(Pedido pedido);

    Pedido buscarPorId(UUID id);

    List<Pedido> buscarTodos();

    List<Pedido> buscarPorStatus(EnumStatusPedido Status);

    List<Pedido> buscarPorClienteId(UUID clienteId);

    void deleteById(UUID id);

    List<Pedido> buscarFila();
}
