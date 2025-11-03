package com.fastfood.domain.produto;

import java.math.BigDecimal;
import java.util.UUID;

public class Produto {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private EnumTipoProduto categoria;
    private String imagemUrl;

    public Produto() {}


    public Produto(UUID id, String nome, String descricao, BigDecimal preco, EnumTipoProduto categoria, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.imagemUrl = imagemUrl;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public EnumTipoProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(EnumTipoProduto categoria) {
        this.categoria = categoria;
    }

    public String getImagemUrl() {return imagemUrl;}

    public void setImagemUrl(String imagemUrl) {this.imagemUrl = imagemUrl;}
}
