package com.fastfood.domain.pedido.dto;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoDTOTest {

    @Test
    void deveCriarObjetoCorretamente() {
        String cpf = "12345678900";
        ItemPedidoDTO item = new ItemPedidoDTO(UUID.randomUUID(), 2);
        List<ItemPedidoDTO> itens = List.of(item);

        PedidoDTO dto = new PedidoDTO(cpf, itens);

        assertEquals(cpf, dto.getCpfCliente());
        assertEquals(itens, dto.getItemPedidoDTOS());
    }

    @Test
    void deveTestarSetters() {
        PedidoDTO dto = new PedidoDTO();
        String cpf = "00000000000";
        List<ItemPedidoDTO> itens = List.of();

        dto.setCpfCliente(cpf);
        dto.setItemPedidoDTOS(itens);

        assertEquals(cpf, dto.getCpfCliente());
        assertEquals(itens, dto.getItemPedidoDTOS());
    }
}
