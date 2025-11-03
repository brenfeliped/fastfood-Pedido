package com.fastfood.adapters.out.repositories.impl;

import com.fastfood.adapters.out.entities.JpaPagamentoEntity;
import com.fastfood.adapters.out.entities.JpaPedidoEntity;
import com.fastfood.adapters.out.repositories.JpaPagamentoRepository;
import com.fastfood.adapters.out.repositories.JpaPedidoRepository;
import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.domain.pedido.PedidoNaoEncontradoException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final JpaPagamentoRepository jpaPagamentoRepository;

    private final JpaPedidoRepository jpaPedidoRepository;

    public PagamentoRepositoryImpl(JpaPagamentoRepository jpaPagamentoRepository, JpaPedidoRepository jpaPedidoRepository) {
        this.jpaPagamentoRepository = jpaPagamentoRepository;
        this.jpaPedidoRepository = jpaPedidoRepository;
    }


    @Override
    public Pagamento save(Pagamento pagamento) throws PedidoNaoEncontradoException {

        JpaPedidoEntity pedidoEntity = jpaPedidoRepository.findById(pagamento.getPedidoId())
                .orElseThrow(() -> new PedidoNaoEncontradoException());

        JpaPagamentoEntity  jpaPagamentoEntity = jpaPagamentoRepository.save(JpaPagamentoEntity.fromDomain(pagamento, pedidoEntity));

        return jpaPagamentoEntity.toDomain();
    }

    @Override
    public Pagamento findById(UUID id) {
        if(jpaPagamentoRepository.findById(id).isPresent()) {
            JpaPagamentoEntity entity = jpaPagamentoRepository.findById(id).get();
            return entity.toDomain();
        }
        return null;
    }

    @Override
    public List<Pagamento> findAll() {
        List<JpaPagamentoEntity> listEntity = jpaPagamentoRepository.findAll();

        return listEntity.stream().map(JpaPagamentoEntity::toDomain).toList();
    }

    @Override
    public Pagamento findByPedidoId(UUID pedidoId) {
        JpaPagamentoEntity entity = jpaPagamentoRepository.findByPedidoId(pedidoId);
        return entity.toDomain();
    }

    @Override
    public List<Pagamento> findByStatus(EnumStatusPagamento status) {
        List<JpaPagamentoEntity> entity = jpaPagamentoRepository.findByStatus(status);

        return entity.stream().map(JpaPagamentoEntity::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {

        jpaPagamentoRepository.deleteById(id);
    }
}
