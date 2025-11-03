package com.fastfood.domain.produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository {
    Produto salvar(Produto produto);
    List<Produto> listarTodos();
    Optional<Produto> buscarPorId(UUID id);
    void deletar(UUID id);
    List<Produto> buscarPorCategoria(EnumTipoProduto categoria);
}
