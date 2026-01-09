package com.fastfood.adapters.out.entities;

import com.fastfood.domain.cliente.Cliente;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaClienteEntityTest {

    @Test
    void deveConverterParaDominio() {
        UUID id = UUID.randomUUID();
        JpaClienteEntity entity = new JpaClienteEntity();
        entity.setId(id);
        entity.setNome("João");
        entity.setEmail("joao@email.com");
        entity.setCpf("12345678900");

        Cliente cliente = entity.toDomain();

        assertNotNull(cliente);
        assertEquals(id, cliente.getId());
        assertEquals("João", cliente.getNome());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("12345678900", cliente.getCpf());
    }

    @Test
    void deveCriarAPartirDoDominio() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente("João", "joao@email.com", "12345678900", id);

        JpaClienteEntity entity = new JpaClienteEntity(cliente);

        assertEquals(id, entity.getId());
        assertEquals("João", entity.getNome());
        assertEquals("joao@email.com", entity.getEmail());
        assertEquals("12345678900", entity.getCpf());
    }

    @Test
    void deveTestarConstrutorVazioESetters() {
        JpaClienteEntity entity = new JpaClienteEntity();
        UUID id = UUID.randomUUID();

        entity.setId(id);
        entity.setNome("Maria");
        entity.setEmail("maria@email.com");
        entity.setCpf("00987654321");

        assertEquals(id, entity.getId());
        assertEquals("Maria", entity.getNome());
        assertEquals("maria@email.com", entity.getEmail());
        assertEquals("00987654321", entity.getCpf());
    }
}
