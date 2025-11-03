
package com.fastfood.domain.exceptions;

import java.util.UUID;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(UUID id) {
        super("Cliente com ID " + id + " não encontrado.");
    }
}