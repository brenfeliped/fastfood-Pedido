package com.fastfood.domain.exceptions;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    void deveCriarPedidoNaoEncontradoException() {
        UUID id = UUID.randomUUID();
        PedidoNaoEncontradoException ex = new PedidoNaoEncontradoException(id);
        assertEquals("Pedido com ID " + id + " não encontrado.", ex.getMessage());
    }

    @Test
    void deveCriarClienteNaoEncontradoException() {
        UUID id = UUID.randomUUID();
        ClienteNaoEncontradoException ex = new ClienteNaoEncontradoException(id);
        assertEquals("Cliente com ID " + id + " não encontrado.", ex.getMessage());
    }

    @Test
    void deveCriarProdutoNaoEncontradoException() {
        UUID id = UUID.randomUUID();
        ProdutoNaoEncontradoException ex = new ProdutoNaoEncontradoException(id);
        assertEquals("Produto com ID " + id + " não encontrado.", ex.getMessage());
    }
}
