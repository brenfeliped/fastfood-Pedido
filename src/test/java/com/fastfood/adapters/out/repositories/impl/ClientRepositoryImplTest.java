package com.fastfood.adapters.out.repositories.impl;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import com.fastfood.adapters.out.repositories.JpaClienteRepository;
import com.fastfood.domain.cliente.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryImplTest {

    @Mock
    private JpaClienteRepository jpaClienteRepository;

    @InjectMocks
    private ClientRepositoryImpl clientRepository;

    @Test
    void deveSalvarCliente() {
        Cliente cliente = new Cliente("João", "joao@email.com", "12345678900", null);
        JpaClienteEntity entity = new JpaClienteEntity(cliente);
        entity.setId(UUID.randomUUID());

        when(jpaClienteRepository.save(any(JpaClienteEntity.class))).thenReturn(entity);

        Cliente salvo = clientRepository.save(cliente);

        assertNotNull(salvo);
        assertNotNull(salvo.getId());
        assertEquals(cliente.getCpf(), salvo.getCpf());
    }

    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        JpaClienteEntity entity = new JpaClienteEntity();
        entity.setId(id);
        entity.setCpf("123");

        when(jpaClienteRepository.findById(id)).thenReturn(Optional.of(entity));

        Cliente encontrado = clientRepository.findById(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
    }

    @Test
    void deveListarTodos() {
        JpaClienteEntity entity = new JpaClienteEntity();
        entity.setCpf("123");
        when(jpaClienteRepository.findAll()).thenReturn(List.of(entity));

        List<Cliente> clientes = clientRepository.findAll();

        assertFalse(clientes.isEmpty());
        assertEquals(1, clientes.size());
    }

    @Test
    void deveDeletarPorId() {
        UUID id = UUID.randomUUID();
        doNothing().when(jpaClienteRepository).deleteById(id);

        clientRepository.deleteById(id);

        verify(jpaClienteRepository).deleteById(id);
    }

    @Test
    void deveBuscarPorCpf() {
        String cpf = "12345678900";
        JpaClienteEntity entity = new JpaClienteEntity();
        entity.setCpf(cpf);

        when(jpaClienteRepository.findByCPF(cpf)).thenReturn(entity);

        Cliente encontrado = clientRepository.findByCPF(cpf);

        assertNotNull(encontrado);
        assertEquals(cpf, encontrado.getCpf());
    }

    @Test
    void deveRetornarNullSeCpfNaoEncontrado() {
        String cpf = "12345678900";
        when(jpaClienteRepository.findByCPF(cpf)).thenReturn(null);

        Cliente encontrado = clientRepository.findByCPF(cpf);

        assertNull(encontrado);
    }
}
