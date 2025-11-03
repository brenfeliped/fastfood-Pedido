package com.fastfood.application.pagamento;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.domain.pedido.PedidoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;

    }

    public Pagamento salvarPagamento(Pagamento pagamento){

        try {
            return  pagamentoRepository.save(pagamento);
        } catch (PedidoNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pagamento> listarTodosPagamentos(){
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(UUID id){
        return pagamentoRepository.findById(id);
    }

    public Pagamento buscarPorPedidoId(UUID id){
        return pagamentoRepository.findByPedidoId(id);
    }

    public List<Pagamento> buscarPorStatus(EnumStatusPagamento status){

        return pagamentoRepository.findByStatus(status);
    }
}
