package com.fastfood.adapters.out.entities;

import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.ItemPedido;
import com.fastfood.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido",
        indexes = {
                @Index(name = "idx_pedido_cliente_id", columnList = "cliente_id"),
                @Index(name = "idx_pedido_status", columnList = "status"),
                @Index(name = "idx_pedido_atualizado_em", columnList = "atualizado_em")
        })
public class JpaPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id" , nullable = false)
    private JpaClienteEntity cliente;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "itens_pedido", joinColumns = @JoinColumn(name = "pedido_id"))
    private List<JpaItemPedido> itens;

    @Enumerated(EnumType.STRING)
    private EnumStatusPedido status;

    private BigDecimal total;

    private int senhaPainel;

    private LocalDateTime atualizadoEm;

    public Pedido toDomain(){
        UUID clienteId = this.cliente == null ? null :  this.cliente.getId();
        List<ItemPedido> domainItens = this.itens == null ? List.of() : this.itens.stream()
                .map(jpaItem -> new ItemPedido(jpaItem.getProdutoId(), null, null, null, jpaItem.getQuantidade(), jpaItem.getPrecoUnitario()))
                .collect(Collectors.toList());

        return  new Pedido(this.id, clienteId, this.getStatus(), this.getTotal(), this.getSenhaPainel(), this.atualizadoEm, domainItens);
    }

    public static JpaPedidoEntity fromDomain(Pedido pedido, JpaClienteEntity cliente){

        JpaPedidoEntity pedidoEntity = new JpaPedidoEntity();
        pedidoEntity.setId(pedido.getId());
        pedidoEntity.setAtualizadoEm(pedido.getAtualizadoEm());
        pedidoEntity.setSenhaPainel(pedido.getSenhaPainel());
        pedidoEntity.setStatus(pedido.getStatus());
        pedidoEntity.setTotal(pedido.getTotal());
        pedidoEntity.setCliente(cliente);

        if (pedido.getItens() != null) {
            pedidoEntity.setItens(pedido.getItens().stream()
                    .map(item -> new JpaItemPedido(item.getProdutoId(), item.getQuantidade(), item.getPrecoUnitario()))
                    .collect(Collectors.toList()));
        }

        return pedidoEntity;
    }
}
