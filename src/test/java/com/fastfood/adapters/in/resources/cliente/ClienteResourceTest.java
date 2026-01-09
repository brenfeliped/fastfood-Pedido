package com.fastfood.adapters.in.resources.cliente;

import com.fastfood.application.cliente.ClienteService;
import com.fastfood.application.security.AuthService;
import com.fastfood.application.security.JwtService;
import com.fastfood.application.security.dto.AuthRequest;
import com.fastfood.application.security.dto.AuthResponse;
import com.fastfood.domain.cliente.Cliente;
import com.fastfood.domain.cliente.dto.ClienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ClienteResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarCliente() throws Exception {
        // A ordem dos argumentos no construtor do DTO é: cpf, nome, email
        ClienteDTO clienteDTO = new ClienteDTO("12345678900", "João", "joao@email.com");
        Cliente cliente = new Cliente("João", "joao@email.com", "12345678900", UUID.randomUUID());

        when(clienteService.buscarPorCpf("12345678900")).thenReturn(null);
        when(clienteService.cadastrarCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes/novo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.cpf").value("12345678900"));
    }

    @Test
    void deveRetornarConflitoSeClienteJaExiste() throws Exception {
        // A ordem dos argumentos no construtor do DTO é: cpf, nome, email
        ClienteDTO clienteDTO = new ClienteDTO("12345678900", "João", "joao@email.com");
        Cliente clienteExistente = new Cliente("João", "joao@email.com", "12345678900", UUID.randomUUID());

        when(clienteService.buscarPorCpf("12345678900")).thenReturn(clienteExistente);

        mockMvc.perform(post("/api/clientes/novo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveBuscarPorCpf() throws Exception {
        String cpf = "12345678900";
        Cliente cliente = new Cliente("João", "joao@email.com", cpf, UUID.randomUUID());

        when(clienteService.buscarPorCpf(cpf)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/busca/{cpf}", cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf));
    }

    @Test
    void deveRetornarNotFoundSeClienteNaoExiste() throws Exception {
        String cpf = "12345678900";
        when(clienteService.buscarPorCpf(cpf)).thenReturn(null);

        mockMvc.perform(get("/api/clientes/busca/{cpf}", cpf))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAutenticarCliente() throws Exception {
        String cpf = "12345678900";
        Cliente cliente = new Cliente("João", "joao@email.com", cpf, UUID.randomUUID());
        AuthResponse authResponse = new AuthResponse("token-jwt");

        when(clienteService.buscarPorCpf(cpf)).thenReturn(cliente);
        when(authService.gerarToken(any(AuthRequest.class))).thenReturn(authResponse);

        mockMvc.perform(get("/api/clientes/auth/{cpf}", cpf))
                .andExpect(status().isOk())
                // O campo no JSON é "access_token" (definido via @JsonProperty), não "token"
                .andExpect(jsonPath("$.access_token").value("token-jwt"));
    }

    @Test
    void deveRetornarForbiddenSeFalharAutenticacao() throws Exception {
        String cpf = "12345678900";
        Cliente cliente = new Cliente("João", "joao@email.com", cpf, UUID.randomUUID());

        when(clienteService.buscarPorCpf(cpf)).thenReturn(cliente);
        when(authService.gerarToken(any(AuthRequest.class))).thenReturn(null);

        mockMvc.perform(get("/api/clientes/auth/{cpf}", cpf))
                .andExpect(status().isForbidden());
    }
}
