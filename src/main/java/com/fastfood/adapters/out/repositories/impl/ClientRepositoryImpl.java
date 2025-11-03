package com.fastfood.adapters.out.repositories.impl;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.domain.cliente.Cliente;
import com.fastfood.domain.cliente.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClienteRepository {

    private final JpaClienteRepository jpaClienteRepository;

    protected ClientRepositoryImpl(JpaClienteRepository jpaClienteRepository) {
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {


        return jpaClienteRepository.save(JpaClienteEntity.fromDomain(cliente)).toDomain();
    }

    @Override
    public Cliente findById(UUID id) {

        return jpaClienteRepository.findById(id).get().toDomain();
    }

    @Override
    public List<Cliente> findAll() {

        return jpaClienteRepository.findAll().stream().map(JpaClienteEntity::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaClienteRepository.deleteById(id);
    }

    @Override
    public Cliente findByCPF(String cpf) {
        JpaClienteEntity clienteEntity =jpaClienteRepository.findByCPF(cpf);
        return clienteEntity == null ? null : clienteEntity.toDomain();
    }
}
