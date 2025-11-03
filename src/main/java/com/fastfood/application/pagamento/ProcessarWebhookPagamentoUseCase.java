package com.fastfood.application.pagamento;


import com.fastfood.application.pedido.PedidoService;
import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.dto.WebhookPagamentoDTO;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import org.springframework.stereotype.Service;

@Service
public class ProcessarWebhookPagamentoUseCase {

    private final PagamentoService pagamentoService;
    private final PedidoService pedidoService;

    public ProcessarWebhookPagamentoUseCase(PagamentoService pagamentoService, PedidoService pedidoService) {
        this.pagamentoService = pagamentoService;
        this.pedidoService = pedidoService;
    }

    public void processar(WebhookPagamentoDTO dto) {
        Pagamento pagamento = pagamentoService.buscarPorId(dto.getIdPagamento());
        Pedido pedido = pedidoService.buscarPorId(pagamento.getPedidoId());
        pedidoService.atualizarStatus(pedido.getId(), EnumStatusPedido.EM_PREPARACAO.name());
        pagamento.setStatus(EnumStatusPagamento.APROVADO);
        pagamentoService.salvarPagamento(pagamento);
        System.out.println("Recebido pagamento: " + dto.getIdPagamento() + ", status: " + dto.getStatus());
    }
}
