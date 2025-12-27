package com.fastfood.adapters.out.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class JpaItemPedido {

    private UUID produtoId; // Agora armazenamos apenas o ID do produto
    private int quantidade;
    private BigDecimal precoUnitario;

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
