package com.fastfood.adapters.out.repositories.impl;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.entities.JpaPedidoEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.adapters.out.repositories.JpaPedidoRepository;
import com.fastfood.domain.exceptions.ClienteNaoEncontradoException;
import com.fastfood.domain.exceptions.PedidoNaoEncontradoException;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import com.fastfood.domain.pedido.PedidoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final JpaPedidoRepository jpaPedidoRepository;
    private final JpaClienteRepository jpaClienteRepository;

    public PedidoRepositoryImpl(JpaPedidoRepository jpaPedidoRepository, JpaClienteRepository jpaClienteRepository) {
        this.jpaPedidoRepository = jpaPedidoRepository;
        this.jpaClienteRepository = jpaClienteRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        UUID clienteId = pedido.getClienteId();

        JpaClienteEntity cliente = jpaClienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));

        return jpaPedidoRepository.save(JpaPedidoEntity.fromDomain(pedido, cliente)).toDomain();
    }

    public Pedido salvar(Pedido pedido, JpaClienteEntity cliente) {
        return jpaPedidoRepository.save(JpaPedidoEntity.fromDomain(pedido, cliente)).toDomain();
    }

    @Override
    public Pedido buscarPorId(UUID id) {
        return jpaPedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id))
                .toDomain();
    }

    @Override
    public List<Pedido> buscarTodos() {
        return jpaPedidoRepository.findAll().stream().map(JpaPedidoEntity::toDomain).toList();
    }

    @Override
    public List<Pedido> buscarPorStatus(EnumStatusPedido status) {
        return jpaPedidoRepository.findByStatus(status).stream().map(JpaPedidoEntity::toDomain).toList();
    }

    @Override
    public List<Pedido> buscarPorClienteId(UUID clienteId) {
        return jpaPedidoRepository.findByClienteId(clienteId).stream().map(JpaPedidoEntity::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaPedidoRepository.deleteById(id);
    }

    @Override
    public List<Pedido> buscarFila() {
        return jpaPedidoRepository.listarFilaPedidos().stream()
                .map(JpaPedidoEntity::toDomain)
                .toList();
    }
}
