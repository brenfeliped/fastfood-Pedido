package com.fastfood.domain.cliente.dto;

import com.fastfood.domain.cliente.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDTOTest {

    @Test
    void deveCriarObjetoCorretamente() {
        String cpf = "12345678900";
        String nome = "João";
        String email = "joao@email.com";

        ClienteDTO dto = new ClienteDTO(cpf, nome, email);

        assertEquals(cpf, dto.getCpf());
        assertEquals(nome, dto.getNome());
        assertEquals(email, dto.getEmail());
    }

    @Test
    void deveConverterParaCliente() {
        ClienteDTO dto = new ClienteDTO("12345678900", "João", "joao@email.com");

        Cliente cliente = dto.convertToClient();

        assertNotNull(cliente);
        assertEquals(dto.getCpf(), cliente.getCpf());
        assertEquals(dto.getNome(), cliente.getNome());
        assertEquals(dto.getEmail(), cliente.getEmail());
    }

    @Test
    void deveTestarSetters() {
        ClienteDTO dto = new ClienteDTO();
        dto.setCpf("000");
        dto.setNome("Teste");
        dto.setEmail("teste@email.com");

        assertEquals("000", dto.getCpf());
        assertEquals("Teste", dto.getNome());
        assertEquals("teste@email.com", dto.getEmail());
    }
}
