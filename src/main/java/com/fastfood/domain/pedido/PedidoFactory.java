package com.fastfood.domain.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PedidoFactory {

    public static Pedido criarNovo(UUID clienteId, List<ItemPedido> itens) {
        BigDecimal total = itens.stream()
                .map(item -> {
                    BigDecimal preco = item.getPrecoUnitario();
                    if (preco == null) {
                        preco = BigDecimal.ZERO;
                    }
                    return preco.multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int senhaPainel = gerarSenhaPainel();

        return new Pedido(
                null,
                clienteId,
                EnumStatusPedido.RECEBIDO,
                total,
                senhaPainel,
                LocalDateTime.now(),
                itens
        );
    }

    private static int gerarSenhaPainel() {
        return new Random().nextInt(9000) + 1000;
    }
}
