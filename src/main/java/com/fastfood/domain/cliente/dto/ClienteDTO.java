package com.fastfood.domain.cliente.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastfood.domain.cliente.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDTO {

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;


    public Cliente convertToClient(){
        Cliente cliente = new Cliente();
        cliente.setCpf(this.cpf);
        cliente.setEmail(this.email);
        cliente.setNome(this.nome);
        return cliente;
    }
}
