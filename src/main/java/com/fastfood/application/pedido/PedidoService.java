package com.fastfood.application.pedido;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.application.pedido.integration.PedidoProducer;
import com.fastfood.application.pedido.integration.ProdutoIntegrationService;
import com.fastfood.application.pedido.integration.dto.PedidoCriadoEvent;
import com.fastfood.application.pedido.integration.dto.ProdutoResponseDTO;
import com.fastfood.domain.exceptions.PedidoNaoEncontradoException;
import com.fastfood.domain.pedido.*;
import com.fastfood.domain.pedido.dto.PedidoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final JpaClienteRepository clienteRepository;
    private final ProdutoIntegrationService produtoIntegrationService;
    private final PedidoProducer pedidoProducer;

    @Transactional
    public Pedido criarPedido(PedidoDTO pedidoDTO) {
        JpaClienteEntity cliente = clienteRepository.findByCPF(pedidoDTO.getCpfCliente());

        List<ItemPedido> itens = pedidoDTO.getItemPedidoDTOS().stream()
                .map(dto -> {
                    ProdutoResponseDTO produto = produtoIntegrationService.buscarProdutoPorId(dto.getProdutoId());
                    return new ItemPedido(
                            produto.getId(),
                            produto.getNome(),
                            produto.getDescricao(),
                            produto.getImagemUrl(),
                            dto.getQuantidade(),
                            produto.getPreco()
                    );
                })
                .toList();

        Pedido pedido = PedidoFactory.criarNovo(cliente.getId(), itens);

        return pedidoRepository.salvar(pedido);
    }

    public Pedido buscarPorId(UUID id) {
        Pedido pedido = pedidoRepository.buscarPorId(id);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException(id);
        }
        return pedido;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.buscarTodos();
    }

    public List<Pedido> listarFilaPedidos() {
        return pedidoRepository.buscarFila();
    }

    public List<Pedido> buscarPorStatus(EnumStatusPedido status) {
        return pedidoRepository.buscarPorStatus(status);
    }

    @Transactional
    public void atualizarStatus(UUID id, String status) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(EnumStatusPedido.valueOf(status.toUpperCase()));
        pedido.setAtualizadoEm(LocalDateTime.now());
        pedidoRepository.salvar(pedido);
    }

    @Transactional
    public void realizarCheckout(UUID id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getStatus() != EnumStatusPedido.RECEBIDO) {
            throw new IllegalStateException("Pedido não pode ser enviado para a cozinha.");
        }
        
        // Envia evento para o Kafka
        PedidoCriadoEvent event = PedidoCriadoEvent.builder()
                .pedidoId(pedido.getId())
                .valor(pedido.getTotal())
                .build();
        pedidoProducer.enviarPedidoCriado(event);

        pedido.setStatus(EnumStatusPedido.EM_PREPARACAO);
        pedido.setAtualizadoEm(LocalDateTime.now());
        pedidoRepository.salvar(pedido);
    }

    @Transactional
    public void marcarComoPronto(UUID id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getStatus() != EnumStatusPedido.EM_PREPARACAO) {
            throw new IllegalStateException("Pedido não está EM_PREPARACAO.");
        }
        pedido.setStatus(EnumStatusPedido.PRONTO);
        pedido.setAtualizadoEm(LocalDateTime.now());
        pedidoRepository.salvar(pedido);
    }

    @Transactional
    public void finalizarPedido(UUID id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getStatus() != EnumStatusPedido.PRONTO) {
            throw new IllegalStateException("Pedido não está PRONTO.");
        }
        pedido.setStatus(EnumStatusPedido.FINALIZADO);
        pedido.setAtualizadoEm(LocalDateTime.now());
        pedidoRepository.salvar(pedido);
    }

    @Transactional
    public void avancarStatus(UUID id) {
        Pedido pedido = buscarPorId(id);
        pedido.avancarStatus();
        pedidoRepository.salvar(pedido);
    }
}
