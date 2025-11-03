package com.fastfood.domain.cliente;

import com.fastfood.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Cliente findById(UUID id);

    List<Cliente> findAll();

    void deleteById(UUID id);


    Cliente findByCPF(String cpf);
}
