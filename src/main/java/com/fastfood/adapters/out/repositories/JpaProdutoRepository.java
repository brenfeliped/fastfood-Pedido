package com.fastfood.adapters.out.repositories;

import com.fastfood.adapters.out.entities.JpaProdutoEntity;
import com.fastfood.domain.produto.EnumTipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProdutoRepository extends JpaRepository<JpaProdutoEntity, UUID> {
    List<JpaProdutoEntity> findByCategoria(EnumTipoProduto categoria);
}
