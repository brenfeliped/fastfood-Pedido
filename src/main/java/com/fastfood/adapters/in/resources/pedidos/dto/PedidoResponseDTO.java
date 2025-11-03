package com.fastfood.adapters.in.resources.pedidos.dto;

import com.fastfood.domain.pedido.EnumStatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PedidoResponseDTO {
    private UUID id;
    private UUID clienteId;
    private EnumStatusPedido status;
    private BigDecimal total;
    private int senhaPainel;
    private LocalDateTime atualizadoEm;

    public PedidoResponseDTO(UUID id, UUID clienteId, EnumStatusPedido status, BigDecimal total, int senhaPainel, LocalDateTime atualizadoEm) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.total = total;
        this.senhaPainel = senhaPainel;
        this.atualizadoEm = atualizadoEm;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public EnumStatusPedido getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getSenhaPainel() {
        return senhaPainel;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
