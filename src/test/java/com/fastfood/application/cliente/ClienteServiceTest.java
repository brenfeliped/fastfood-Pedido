package com.fastfood.application.cliente;

import com.fastfood.domain.cliente.Cliente;
import com.fastfood.domain.cliente.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveCadastrarCliente() {
        Cliente cliente = new Cliente("João", "joao@email.com", "12345678900", null);
        Cliente clienteSalvo = new Cliente("João", "joao@email.com", "12345678900", UUID.randomUUID());

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        Cliente resultado = clienteService.cadastrarCliente(cliente);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(cliente.getCpf(), resultado.getCpf());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente("João", "joao@email.com", "12345678900", id);

        when(clienteRepository.findById(id)).thenReturn(cliente);

        Cliente resultado = clienteService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(clienteRepository).findById(id);
    }

    @Test
    void deveBuscarPorCpf() {
        String cpf = "12345678900";
        Cliente cliente = new Cliente("João", "joao@email.com", cpf, UUID.randomUUID());

        when(clienteRepository.findByCPF(cpf)).thenReturn(cliente);

        Cliente resultado = clienteService.buscarPorCpf(cpf);

        assertNotNull(resultado);
        assertEquals(cpf, resultado.getCpf());
        verify(clienteRepository).findByCPF(cpf);
    }
}
