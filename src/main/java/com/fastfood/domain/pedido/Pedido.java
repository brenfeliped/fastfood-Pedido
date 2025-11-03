package com.fastfood.domain.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Pedido {
    private UUID id;
    private UUID clienteId;
    private EnumStatusPedido status;
    private BigDecimal total;
    private int senhaPainel;
    private LocalDateTime atualizadoEm;


    public Pedido(UUID id, UUID clienteId, EnumStatusPedido status, BigDecimal total, int senhaPainel, LocalDateTime atualizadoEm) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.total = total;
        this.senhaPainel = senhaPainel;
        this.atualizadoEm = atualizadoEm;
    }

    // Construtor vazio
    public Pedido() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public EnumStatusPedido getStatus() {
        return status;
    }

    public void setStatus(EnumStatusPedido status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getSenhaPainel() {
        return senhaPainel;
    }

    public void setSenhaPainel(int senhaPainel) {
        this.senhaPainel = senhaPainel;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public void avancarStatus() {
        switch (this.status) {
            case RECEBIDO -> this.status = EnumStatusPedido.EM_PREPARACAO;
            case EM_PREPARACAO -> this.status = EnumStatusPedido.PRONTO;
            case PRONTO -> this.status = EnumStatusPedido.FINALIZADO;
            default -> throw new IllegalStateException("Pedido já finalizado.");
        }
        this.atualizadoEm = LocalDateTime.now();
    }
}
