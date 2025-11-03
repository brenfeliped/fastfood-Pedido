package com.fastfood.adapters.out.entities;


import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaPagamentoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

//    @NotNull
//    private UUID pedidoId;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false, unique = true)
    private JpaPedidoEntity pedido;

    @Enumerated(EnumType.STRING)
    private EnumStatusPagamento status;

    public Pagamento toDomain(){
        return new Pagamento(this.id, this.pedido.getId(), status);
    }

    public static JpaPagamentoEntity fromDomain(Pagamento pagamento , JpaPedidoEntity pedidoEntity){
        JpaPagamentoEntity pagamentoEntity = new JpaPagamentoEntity();
        pagamentoEntity.setId(pagamento.getId());
        //pagamentoEntity.setPedidoId(pagamento.getPedidoId());
        pagamentoEntity.setPedido(pedidoEntity);
        pagamentoEntity.setStatus(pagamento.getStatus());
        return  pagamentoEntity;
    }
}
