package com.fastfood.adapters.out.entities;

import com.fastfood.domain.pedido.EnumStatusPedido;
import com.fastfood.domain.pedido.ItemPedido;
import com.fastfood.domain.pedido.Pedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaPedidoEntityTest {

    @Test
    void deveConverterParaDominio() {
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();

        JpaPedidoEntity entity = new JpaPedidoEntity();
        entity.setId(id);
        
        JpaClienteEntity clienteEntity = new JpaClienteEntity();
        clienteEntity.setId(clienteId);
        entity.setCliente(clienteEntity);
        
        entity.setStatus(EnumStatusPedido.RECEBIDO);
        entity.setTotal(BigDecimal.TEN);
        entity.setSenhaPainel(123);
        entity.setAtualizadoEm(agora);
        
        JpaItemPedido itemEntity = new JpaItemPedido();
        itemEntity.setProdutoId(UUID.randomUUID());
        itemEntity.setQuantidade(1);
        itemEntity.setPrecoUnitario(BigDecimal.TEN);
        
        entity.setItens(List.of(itemEntity));

        Pedido pedido = entity.toDomain();

        assertNotNull(pedido);
        assertEquals(id, pedido.getId());
        assertEquals(clienteId, pedido.getClienteId());
        assertEquals(EnumStatusPedido.RECEBIDO, pedido.getStatus());
        assertEquals(1, pedido.getItens().size());
    }

    @Test
    void deveCriarAPartirDoDominio() {
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();
        
        ItemPedido item = new ItemPedido(UUID.randomUUID(), "Lanche", "Desc", "url", 1, BigDecimal.TEN);
        Pedido pedido = new Pedido(id, clienteId, EnumStatusPedido.RECEBIDO, BigDecimal.TEN, 123, agora, List.of(item));
        
        JpaClienteEntity clienteEntity = new JpaClienteEntity();
        clienteEntity.setId(clienteId);

        JpaPedidoEntity entity = JpaPedidoEntity.fromDomain(pedido, clienteEntity);

        assertEquals(id, entity.getId());
        assertEquals(clienteId, entity.getCliente().getId());
        assertEquals(EnumStatusPedido.RECEBIDO, entity.getStatus());
        assertEquals(1, entity.getItens().size());
    }
    
    @Test
    void deveTestarSetters() {
        JpaPedidoEntity entity = new JpaPedidoEntity();
        UUID id = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();
        List<JpaItemPedido> itens = List.of(new JpaItemPedido());
        
        JpaClienteEntity clienteEntity = new JpaClienteEntity();
        clienteEntity.setId(clienteId);

        entity.setId(id);
        entity.setCliente(clienteEntity);
        entity.setStatus(EnumStatusPedido.PRONTO);
        entity.setTotal(BigDecimal.valueOf(50));
        entity.setSenhaPainel(999);
        entity.setAtualizadoEm(agora);
        entity.setItens(itens);

        assertEquals(id, entity.getId());
        assertEquals(clienteEntity, entity.getCliente());
        assertEquals(EnumStatusPedido.PRONTO, entity.getStatus());
        assertEquals(BigDecimal.valueOf(50), entity.getTotal());
        assertEquals(999, entity.getSenhaPainel());
        assertEquals(agora, entity.getAtualizadoEm());
        assertEquals(itens, entity.getItens());
    }
}
