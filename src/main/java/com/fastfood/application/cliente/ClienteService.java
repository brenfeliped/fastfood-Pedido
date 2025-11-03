package com.fastfood.application.cliente;

import com.fastfood.domain.cliente.Cliente;
import com.fastfood.domain.cliente.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(UUID id){

        return clienteRepository.findById(id);
    }

    public Cliente atualizar(UUID id, Cliente cliente){
        Cliente clienteId = clienteRepository.findById(id);
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void deletar(UUID id){
        clienteRepository.deleteById(id);
    }

    public Cliente buscarPorCpf(String cpf){
       return  clienteRepository.findByCPF(cpf);
    }
}
