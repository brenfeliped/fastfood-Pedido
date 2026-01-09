package com.fastfood.adapters.out.repositories.impl;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.entities.JpaPedidoEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.adapters.out.repositories.JpaPedidoRepository;
import com.fastfood.domain.exceptions.ClienteNaoEncontradoException;
import com.fastfood.domain.exceptions.PedidoNaoEncontradoException;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoRepositoryImplTest {

    @Mock
    private JpaPedidoRepository jpaPedidoRepository;

    @Mock
    private JpaClienteRepository jpaClienteRepository;

    @InjectMocks
    private PedidoRepositoryImpl pedidoRepository;

    @Test
    void deveSalvarPedido() {
        UUID clienteId = UUID.randomUUID();
        Pedido pedido = new Pedido(UUID.randomUUID(), clienteId, EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        JpaClienteEntity clienteEntity = new JpaClienteEntity();
        clienteEntity.setId(clienteId);
        
        JpaPedidoEntity pedidoEntity = JpaPedidoEntity.fromDomain(pedido, clienteEntity);

        when(jpaClienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteEntity));
        when(jpaPedidoRepository.save(any(JpaPedidoEntity.class))).thenReturn(pedidoEntity);

        Pedido salvo = pedidoRepository.salvar(pedido);

        assertNotNull(salvo);
        assertEquals(pedido.getId(), salvo.getId());
    }

    @Test
    void deveLancarExcecaoAoSalvarSeClienteNaoExiste() {
        UUID clienteId = UUID.randomUUID();
        Pedido pedido = new Pedido(UUID.randomUUID(), clienteId, EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());

        when(jpaClienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> pedidoRepository.salvar(pedido));
    }

    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        JpaPedidoEntity entity = new JpaPedidoEntity();
        entity.setId(id);
        entity.setCliente(new JpaClienteEntity());

        when(jpaPedidoRepository.findById(id)).thenReturn(Optional.of(entity));

        Pedido encontrado = pedidoRepository.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }

    @Test
    void deveLancarExcecaoSePedidoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(jpaPedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoRepository.buscarPorId(id));
    }

    @Test
    void deveListarTodos() {
        JpaPedidoEntity entity = new JpaPedidoEntity();
        entity.setCliente(new JpaClienteEntity());
        when(jpaPedidoRepository.findAll()).thenReturn(List.of(entity));

        List<Pedido> pedidos = pedidoRepository.buscarTodos();

        assertFalse(pedidos.isEmpty());
    }

    @Test
    void deveBuscarPorStatus() {
        JpaPedidoEntity entity = new JpaPedidoEntity();
        entity.setCliente(new JpaClienteEntity());
        when(jpaPedidoRepository.findByStatus(EnumStatusPedido.RECEBIDO)).thenReturn(List.of(entity));

        List<Pedido> pedidos = pedidoRepository.buscarPorStatus(EnumStatusPedido.RECEBIDO);

        assertFalse(pedidos.isEmpty());
    }

    @Test
    void deveBuscarPorClienteId() {
        UUID clienteId = UUID.randomUUID();
        JpaPedidoEntity entity = new JpaPedidoEntity();
        entity.setCliente(new JpaClienteEntity());
        when(jpaPedidoRepository.findByClienteId(clienteId)).thenReturn(List.of(entity));

        List<Pedido> pedidos = pedidoRepository.buscarPorClienteId(clienteId);

        assertFalse(pedidos.isEmpty());
    }

    @Test
    void deveDeletarPorId() {
        UUID id = UUID.randomUUID();
        doNothing().when(jpaPedidoRepository).deleteById(id);

        pedidoRepository.deleteById(id);

        verify(jpaPedidoRepository).deleteById(id);
    }

    @Test
    void deveBuscarFila() {
        JpaPedidoEntity entity = new JpaPedidoEntity();
        entity.setCliente(new JpaClienteEntity());
        when(jpaPedidoRepository.listarFilaPedidos()).thenReturn(List.of(entity));

        List<Pedido> fila = pedidoRepository.buscarFila();

        assertFalse(fila.isEmpty());
    }
}
