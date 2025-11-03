
# 🍔 FastFood - Backend

Backend monolítico desenvolvido para o **Desafio SOAT Tech Challenge - Fast Food - Fase 1**, utilizando **Arquitetura Hexagonal** com Java e Spring Boot.

---

## ✅ Tecnologias utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Docker e Docker Compose
- Swagger (SpringDoc OpenAPI)

---

## ✅ Arquitetura

Este projeto segue a **Arquitetura Hexagonal**, organizando as responsabilidades em:

- **Domain:** entidades de negócio e repositórios.
- **Application:** casos de uso e regras de negócio.
- **Adapters:** entrada (REST Controllers) e saída (persistência, filas).
- **Configuration:** mapeamentos e configurações.

---

## ✅ Funcionalidades

- Cadastro e busca de cliente por CPF
- CRUD de produtos
- Busca de produtos por categoria
- Fake checkout (envio de pedido para fila simulada)
- Listagem de pedidos
- Documentação automática via Swagger

---

## ✅ Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## ✅ Como executar o projeto

1. Suba a aplicação com Docker Compose:
```bash
docker-compose up -d --build 
```

2. Acesse a aplicação:

- API: [http://localhost:8080](http://localhost:8080/fastfood)
- Swagger: [http://localhost:8080/fastfood/swagger-ui/index.html](http://localhost:8080/fastfood/swagger-ui.html)

