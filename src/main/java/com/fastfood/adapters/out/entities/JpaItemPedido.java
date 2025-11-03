package com.fastfood.adapters.out.entities;

import com.fastfood.domain.pedido.Pedido;
import com.fastfood.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class JpaItemPedido {

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private JpaProdutoEntity produto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
