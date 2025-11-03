package com.fastfood.domain.pedido;

import com.fastfood.domain.produto.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PedidoFactory {

    public static Pedido criarNovo(UUID clienteId, List<ItemPedido> itens, ProdutoFinder produtoFinder) {
        BigDecimal total = itens.stream()
                .map(item -> {
                    Produto produto = produtoFinder.buscarPorId(item.getProdutoId());
                    return produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int senhaPainel = gerarSenhaPainel();

        return new Pedido(
                null,
                clienteId,
                EnumStatusPedido.RECEBIDO,
                total,
                senhaPainel,
                LocalDateTime.now()
        );
    }

    private static int gerarSenhaPainel() {
        return new Random().nextInt(9000) + 1000;
    }

    public interface ProdutoFinder {
        Produto buscarPorId(UUID id);
    }
}
