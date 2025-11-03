package com.fastfood.adapters.out.repositories.impl;

import com.fastfood.adapters.out.entities.JpaProdutoEntity;
import com.fastfood.adapters.out.repositories.JpaProdutoRepository;
import com.fastfood.domain.produto.EnumTipoProduto;
import com.fastfood.domain.produto.Produto;
import com.fastfood.domain.produto.ProdutoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final JpaProdutoRepository jpa;

    public ProdutoRepositoryImpl(JpaProdutoRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Produto salvar(Produto produto) {
        return jpa.save(JpaProdutoEntity.fromDomain(produto)).toDomain();
    }

    @Override
    public List<Produto> listarTodos() {
        return jpa.findAll().stream().map(JpaProdutoEntity::toDomain).toList();
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return jpa.findById(id).map(JpaProdutoEntity::toDomain);
    }

    @Override
    public void deletar(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public List<Produto> buscarPorCategoria(EnumTipoProduto categoria) {
        return jpa.findByCategoria(categoria).stream()
                .map(JpaProdutoEntity::toDomain).toList();
    }
}
