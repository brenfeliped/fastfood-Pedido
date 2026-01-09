package com.fastfood.adapters.in.resources.pedidos;

import com.fastfood.adapters.in.resources.pedidos.dto.PedidoResponseDTO;
import com.fastfood.application.pedido.PedidoService;
import com.fastfood.application.security.AuthService;
import com.fastfood.application.security.JwtService;
import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.Pedido;
import com.fastfood.domain.pedido.dto.PedidoDTO;
import com.fastfood.domain.pedido.dto.PedidoUpdateStatusDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PedidoResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class PedidoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPedido() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCpfCliente("12345678900");
        
        Pedido pedido = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());

        when(pedidoService.criarPedido(any(PedidoDTO.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("RECEBIDO"));
    }

    @Test
    void deveBuscarPorId() throws Exception {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido(id, UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());

        when(pedidoService.buscarPorId(id)).thenReturn(pedido);

        mockMvc.perform(get("/api/pedidos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deveBuscarPorStatus() throws Exception {
        Pedido pedido = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoService.buscarPorStatus(EnumStatusPedido.RECEBIDO)).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/pedidos/status/RECEBIDO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("RECEBIDO"));
    }

    @Test
    void deveAtualizarStatus() throws Exception {
        UUID id = UUID.randomUUID();
        PedidoUpdateStatusDTO dto = new PedidoUpdateStatusDTO();
        dto.setId(id);
        dto.setEnumStatusPedido(EnumStatusPedido.EM_PREPARACAO);

        doNothing().when(pedidoService).atualizarStatus(id, "EM_PREPARACAO");

        mockMvc.perform(put("/api/pedidos/{id}/status", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveListarFilaPedidos() throws Exception {
        Pedido pedido = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoService.listarFilaPedidos()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/pedidos/fila-pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void deveListarTodos() throws Exception {
        Pedido pedido = new Pedido(UUID.randomUUID(), UUID.randomUUID(), EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, LocalDateTime.now());
        
        when(pedidoService.listarTodos()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void deveRealizarCheckout() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(pedidoService).realizarCheckout(id);

        mockMvc.perform(post("/api/pedidos/{id}/checkout", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(id.toString()));
    }

    @Test
    void deveMarcarComoPronto() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(pedidoService).marcarComoPronto(id);

        mockMvc.perform(patch("/api/pedidos/{id}/pronto", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveFinalizarPedido() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(pedidoService).finalizarPedido(id);

        mockMvc.perform(patch("/api/pedidos/{id}/finalizar", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deveAvancarStatus() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(pedidoService).avancarStatus(id);

        mockMvc.perform(patch("/api/pedidos/{id}/avancar", id))
                .andExpect(status().isNoContent());
    }
}
