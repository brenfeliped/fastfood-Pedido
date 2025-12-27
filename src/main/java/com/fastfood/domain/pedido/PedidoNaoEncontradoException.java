package com.fastfood.domain.pedido;

import java.util.UUID;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(UUID id) {
        super("Pedido com ID " + id + " não encontrado.");
    }
}
