package com.fastfood.adapters.in.resources.cliente;

import com.fastfood.application.cliente.ClienteService;
import com.fastfood.application.security.AuthService;
import com.fastfood.application.security.dto.AuthRequest;
import com.fastfood.application.security.dto.AuthResponse;
import com.fastfood.domain.cliente.Cliente;
import com.fastfood.domain.cliente.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteResource {


    private final ClienteService clienteService;

    private final AuthService authService;

    public ClienteResource(ClienteService clienteService, AuthService authService) {
        this.clienteService = clienteService;
        this.authService = authService;
    }


    @PostMapping("/novo")
    @Operation(
            summary = "Cadastra um novo cliente",
            description = "Cria um novo cliente na base de dados com nome, email e CPF."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Cliente com o mesmo CPF já existe"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public ResponseEntity<Cliente> cadastrarCliente(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do cliente a ser criado", required = true) @RequestBody @Valid ClienteDTO clienteDTO) {
        if (clienteService.buscarPorCpf(clienteDTO.getCpf()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Cliente cliente = clienteService.cadastrarCliente(clienteDTO.convertToClient());
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping("/busca/{cpf}")
    @Operation(
            summary = "Busca/Idetificar cliente por CPF",
            description = "Retorna os dados de um cliente com base no CPF informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorCpf( @Parameter(description = "CPF do cliente a ser consultado", required = true) @PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("auth/{cpf}")
    @Operation(
            summary = "Autentica cliente por CPF",
            description = "Retorna o token de acesso do cliente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<AuthResponse> autentica( @Parameter(description = "CPF do cliente a ser consultado", required = true) @PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        AuthResponse response = authService.gerarToken(new AuthRequest(cpf));
        if(response == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(response);
    }

}
