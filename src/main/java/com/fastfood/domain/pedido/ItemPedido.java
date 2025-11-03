package com.fastfood.domain.pedido;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemPedido {
    private UUID produtoId;
    private String nome;
    private String descricao;
    private String imagemUrl;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido(UUID produtoId, String nome, String descricao, String imagemUrl, int quantidade, BigDecimal precoUnitario) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public UUID getProdutoId() { return produtoId; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getImagemUrl() { return imagemUrl; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
}
