package com.fastfood.domain.produto.dto;

import com.fastfood.domain.produto.Produto;

public class ProdutoMapper {

    public static ProdutoDTO toDTO(Produto produto) {
        if (produto == null) return null;

        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getCategoria(),
                produto.getImagemUrl()
        );
    }

    public static Produto toDomain(ProdutoDTO dto) {
        if (dto == null) return null;

        return new Produto(
                dto.getId(),
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                dto.getCategoria(),
                dto.getImagemUrl()
        );
    }
}
