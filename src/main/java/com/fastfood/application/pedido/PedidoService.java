package com.fastfood.application.pedido;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.entities.JpaProdutoEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.adapters.out.repositories.ProdutoRepository;
import com.fastfood.domain.exceptions.PedidoNaoEncontradoException;
import com.fastfood.domain.exceptions.ProdutoNaoEncontradoException;
import com.fastfood.domain.pedido.*;
import com.fastfood.domain.pedido.dto.PedidoDTO;
import com.fastfood.domain.produto.Produto;
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
    private final ProdutoRepository produtoRepository;
    private final JpaClienteRepository clienteRepository;

    @Transactional
    public Pedido criarPedido(PedidoDTO pedidoDTO) {
        JpaClienteEntity cliente = clienteRepository.findByCPF(pedidoDTO.getCpfCliente());

        List<ItemPedido> itens = pedidoDTO.getItemPedidoDTOS().stream()
                .map(dto -> new ItemPedido(dto.getProdutoId(), null, null, null, dto.getQuantidade(), null))
                .toList();

        Pedido pedido = PedidoFactory.criarNovo(cliente.getId(), itens, produtoId -> {
            JpaProdutoEntity entity = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));

            return new Produto(
                    entity.getId(),
                    entity.getNome(),
                    entity.getDescricao(),
                    entity.getPreco(),
                    entity.getCategoria(),
                    entity.getImagemUrl()
            );
        });

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
