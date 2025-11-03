package com.fastfood.domain.exceptions;

import java.util.UUID;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(UUID id) {
        super("Pedido com ID " + id + " não encontrado.");
    }
}
