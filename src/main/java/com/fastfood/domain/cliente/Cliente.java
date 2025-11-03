package com.fastfood.domain.cliente;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class Cliente {

    private UUID id;
    private String cpf;
    private String nome;
    private String email;

    public Cliente(String nome, String email, String cpf, UUID id) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.id = id;
    }

    public Cliente() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

}
