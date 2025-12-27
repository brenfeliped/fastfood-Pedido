package com.fastfood.domain.cliente;

import java.util.List;
import java.util.UUID;


public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Cliente findById(UUID id);

    List<Cliente> findAll();

    void deleteById(UUID id);


    Cliente findByCPF(String cpf);
}
