package com.fastfood.adapters.out.entities;

import com.fastfood.domain.produto.EnumTipoProduto;
import com.fastfood.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private EnumTipoProduto categoria;

    private BigDecimal preco;

    private String imagemUrl;

    public Produto toDomain() {
        return new Produto(id, nome, descricao, preco, categoria, imagemUrl);
    }

    public static JpaProdutoEntity fromDomain(Produto produto) {
        JpaProdutoEntity entity = new JpaProdutoEntity();
        entity.setId(produto.getId());
        entity.setNome(produto.getNome());
        entity.setDescricao(produto.getDescricao());
        entity.setPreco(produto.getPreco());
        entity.setCategoria(produto.getCategoria());
        entity.setImagemUrl(produto.getImagemUrl());
        return entity;
    }
}
