package com.fastfood.adapters.out.entities;

import com.fastfood.domain.cliente.Cliente;
import com.fastfood.domain.pedido.Pedido;
import com.fastfood.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;


import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "cliente")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaClienteEntity {

    @Column(name = "id_cliente")
    @Id
    @GeneratedValue
    private UUID id;

    private String cpf;
    private String nome;
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<JpaPedidoEntity> pedidos = new ArrayList<>();


    public JpaClienteEntity(Cliente cliente) {
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.id = cliente.getId();
    }

    public  Cliente toDomain(){
        return new Cliente(this.nome, this.email, this.cpf, this.id);
    }
    public static JpaClienteEntity fromDomain(Cliente cliente) {
        JpaClienteEntity clienteEntity = new JpaClienteEntity();
        clienteEntity.setCpf(cliente.getCpf());
        clienteEntity.setEmail(cliente.getEmail());
        clienteEntity.setId(cliente.getId());
        clienteEntity.setNome(cliente.getNome());

        return  clienteEntity;
    }
}
