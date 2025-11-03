package com.fastfood.domain.produto;

public enum EnumTipoProduto {

    LANCHE(1, "Lanche"),
    ACOMPANHAMENTO(2, "Acompanhamento"),
    BEBIDA(3, "Bebida"),
    SOBREMESA(4, "Sobremesa");

    private final Integer id;
    private final String categoria;
    EnumTipoProduto(Integer id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }
}
