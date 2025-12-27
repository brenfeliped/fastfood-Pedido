package com.fastfood.application.pedido;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.application.pedido.integration.ProdutoIntegrationService;
import com.fastfood.application.pedido.integration.dto.ProdutoResponseDTO;
import com.fastfood.domain.exceptions.PedidoNaoEncontradoException;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.ItemPedido;
import com.fastfood.domain.pedido.Pedido;
import com.fastfood.domain.pedido.PedidoRepository;
import com.fastfood.domain.pedido.dto.ItemPedidoDTO;
import com.fastfood.domain.pedido.dto.PedidoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private JpaClienteRepository clienteRepository;

    @Mock
    private ProdutoIntegrationService produtoIntegrationService;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void deveCriarPedidoComSucesso() {
        String cpf = "12345678900";
        UUID produtoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        
        PedidoDTO pedidoDTO = new PedidoDTO(cpf, List.of(new ItemPedidoDTO(produtoId, 2)));
        
        JpaClienteEntity clienteEntity = new JpaClienteEntity();
        clienteEntity.setId(clienteId);
        clienteEntity.setCpf(cpf);
        
        ProdutoResponseDTO produtoDTO = new ProdutoResponseDTO(
            produtoId, "X-Burger", "Delicioso", BigDecimal.valueOf(20.0), "LANCHE", "url"
        );

        when(clienteRepository.findByCPF(cpf)).thenReturn(clienteEntity);
        when(produtoIntegrationService.buscarProdutoPorId(produtoId)).thenReturn(produtoDTO);
        when(pedidoRepository.salvar(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedidoCriado = pedidoService.criarPedido(pedidoDTO);

        assertNotNull(pedidoCriado);
        assertEquals(clienteId, pedidoCriado.getClienteId());
        assertEquals(EnumStatusPedido.RECEBIDO, pedidoCriado.getStatus());
        assertEquals(1, pedidoCriado.getItens().size());
        assertEquals(BigDecimal.valueOf(40.0), pedidoCriado.getTotal()); // 2 * 20.0
        
        verify(clienteRepository).findByCPF(cpf);
        verify(produtoIntegrationService).buscarProdutoPorId(produtoId);
        verify(pedidoRepository).salvar(any(Pedido.class));
    }

    @Test
    void deveBuscarPedidoPorId() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoRepository.buscarPorId(id)).thenReturn(pedido);

        Pedido encontrado = pedidoService.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
        verify(pedidoRepository).buscarPorId(id);
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(pedidoRepository.buscarPorId(id)).thenReturn(null);

        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.buscarPorId(id));
    }

    @Test
    void deveListarTodosPedidos() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.buscarTodos()).thenReturn(Collections.singletonList(pedido));

        List<Pedido> pedidos = pedidoService.listarTodos();

        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
        verify(pedidoRepository).buscarTodos();
    }

    @Test
    void deveListarFilaPedidos() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.buscarFila()).thenReturn(Collections.singletonList(pedido));

        List<Pedido> fila = pedidoService.listarFilaPedidos();

        assertFalse(fila.isEmpty());
        verify(pedidoRepository).buscarFila();
    }

    @Test
    void deveAtualizarStatus() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoRepository.buscarPorId(id)).thenReturn(pedido);

        pedidoService.atualizarStatus(id, "EM_PREPARACAO");

        assertEquals(EnumStatusPedido.EM_PREPARACAO, pedido.getStatus());
        verify(pedidoRepository).salvar(pedido);
    }

    @Test
    void deveRealizarCheckout() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoRepository.buscarPorId(id)).thenReturn(pedido);

        pedidoService.realizarCheckout(id);

        assertEquals(EnumStatusPedido.EM_PREPARACAO, pedido.getStatus());
        verify(pedidoRepository).salvar(pedido);
    }

    @Test
    void deveLancarErroCheckoutSeStatusInvalido() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, UUID.randomUUID(), EnumStatusPedido.PRONTO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoRepository.buscarPorId(id)).thenReturn(pedido);

        assertThrows(IllegalStateException.class, () -> pedidoService.realizarCheckout(id));
    }
}
