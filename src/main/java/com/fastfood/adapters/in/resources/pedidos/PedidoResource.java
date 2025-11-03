package com.fastfood.adapters.in.resources.pedidos;

import com.fastfood.adapters.in.resources.pedidos.dto.PedidoResponseDTO;
import com.fastfood.adapters.in.resources.pedidos.mapper.PedidoMapper;
import com.fastfood.adapters.out.entities.JpaPedidoEntity;
import com.fastfood.application.pedido.PedidoService;
import com.fastfood.domain.pedido.Pedido;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.dto.PedidoDTO;
import com.fastfood.domain.pedido.dto.PedidoUpdateStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações relacionadas a pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;


    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do pedido inválidos")
    })
    public ResponseEntity<PedidoResponseDTO> criarPedido(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do pedido a ser criado", required = true
            )
            @RequestBody PedidoDTO pedidoDTO
    ) {
        Pedido pedidoCriado = pedidoService.criarPedido(pedidoDTO);
        PedidoResponseDTO response = PedidoMapper.toDto(pedidoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os dados do pedido com o ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable UUID id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        PedidoResponseDTO response = PedidoMapper.toDto(pedido);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar pedidos por status", description = "Lista todos os pedidos com o status informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorStatus(
            @Parameter(description = "Status do pedido", required = true)
            @PathVariable EnumStatusPedido status
    ) {
        List<Pedido> pedidos = pedidoService.buscarPorStatus(status);
        List<PedidoResponseDTO> response = PedidoMapper.toDtoList(pedidos);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status do pedido para o valor informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Void> atualizarStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novo status do pedido no formato { \"status\": \"RECIBIDO\", \"EM_PREPARACAO\", \"PRONTO\"" +
                    ", \"FINALIZADO\" }", required = true)
            @RequestBody PedidoUpdateStatusDTO pedidoUpdateStatusDTO
            ) {
        pedidoService.atualizarStatus(pedidoUpdateStatusDTO.getId(), pedidoUpdateStatusDTO.getEnumStatusPedido().name());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/fila-pedidos")
    @Operation(summary = "Listar fila de atendimento pedidos", description = "Retorna todos os pedidos que não foram finalizados em ordem.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoResponseDTO>> listarFilaPedidos() {
        List<Pedido> pedidos = pedidoService.listarFilaPedidos();
        List<PedidoResponseDTO> response = PedidoMapper.toDtoList(pedidos);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna todos os pedidos cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        List<PedidoResponseDTO> response = PedidoMapper.toDtoList(pedidos);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/checkout")
    @Operation(summary = "Realizar checkout do pedido", description = "Inicia o processo de checkout do pedido com o ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checkout realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Map<String, UUID>> realizarCheckout(
            @Parameter(description = "ID do pedido", required = true) @PathVariable UUID id
    ) {
        pedidoService.realizarCheckout(id);
        return ResponseEntity.ok(Map.of("pedidoId", id));
    }

    @PatchMapping("/{id}/pronto")
    @Operation(summary = "Marcar pedido como pronto", description = "Atualiza o status do pedido para PRONTO.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido marcado como pronto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Void> marcarComoPronto( @Parameter(description = "ID do pedido", required = true) @PathVariable UUID id) {
        pedidoService.marcarComoPronto(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar pedido", description = "Atualiza o status do pedido para FINALIZADO.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido finalizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Void> finalizarPedido(@Parameter(description = "ID do pedido", required = true) @PathVariable UUID id) {
        pedidoService.finalizarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/avancar")
    public ResponseEntity<Void> avancar(@PathVariable UUID id) {
        pedidoService.avancarStatus(id);
        return ResponseEntity.noContent().build();
    }

}
