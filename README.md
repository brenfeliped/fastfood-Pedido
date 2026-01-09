# 🍔 FastFood - Pedido Service

Microserviço responsável pelo gerenciamento de pedidos do **FastFood**, desenvolvido para o **Desafio SOAT Tech Challenge - Fast Food - Fase 4**, utilizando **Arquitetura Hexagonal** com Java e Spring Boot.

Este serviço faz parte de uma arquitetura de microserviços e é responsável por:
- Gerenciar o ciclo de vida dos pedidos (Criação, Atualização de Status, Finalização)
- Integração com serviço de Produção (Produtos)
- Integração com serviço de Pagamento (via Kafka)
- Gerenciamento de Clientes e Autenticação

---

## ✅ Tecnologias utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Docker e Docker Compose
- Swagger (SpringDoc OpenAPI)
- Spring Security + JWT
- Apache Kafka (Mensageria)
- Spring Cloud OpenFeign / RestTemplate (Comunicação síncrona)

---

## ✅ Arquitetura

Este projeto segue a **Arquitetura Hexagonal**, organizando as responsabilidades em:

- **Domain:** entidades de negócio e repositórios.
- **Application:** casos de uso e regras de negócio.
- **Adapters:** entrada (REST Controllers) e saída (persistência, filas, integrações externas).
- **Configuration:** mapeamentos e configurações.

---

## ✅ Funcionalidades

- **Clientes:**
  - Cadastro e busca de cliente por CPF
  - Autenticação de cliente via CPF (JWT)

- **Pedidos:**
  - Criação de pedidos (valida produtos no serviço externo)
  - Busca de pedidos por ID e Status
  - Atualização de status do pedido
  - Fila de pedidos (listagem de pedidos não finalizados)
  - Checkout de pedido (envia evento para fila Kafka)
  - Documentação automática via Swagger

---

## ✅ Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## ✅ Como executar o projeto

1. Certifique-se de que a rede `fastfood-network` existe (caso esteja rodando em conjunto com outros microserviços):
```bash
docker network create fastfood-network
```

2. Suba a aplicação com Docker Compose:
```bash
docker-compose up -d --build 
```

3. Acesse a aplicação:

- API: [http://localhost:8082/fastfood-pedido](http://localhost:8082/fastfood-pedido)
- Swagger: [http://localhost:8082/fastfood-pedido/swagger-ui/index.html](http://localhost:8082/fastfood-pedido/swagger-ui/index.html)

### Endpoints Principais

#### Autenticação
- `POST /auth/gerar-token`: Gera token JWT (para CPF ou anônimo)
- `POST /auth/validar-token`: Valida token JWT

#### Clientes
- `POST /api/clientes/novo`: Cadastra novo cliente
- `GET /api/clientes/busca/{cpf}`: Busca cliente por CPF
- `GET /api/clientes/auth/{cpf}`: Autentica cliente por CPF

#### Pedidos
- `POST /api/pedidos`: Cria novo pedido
- `GET /api/pedidos/fila-pedidos`: Lista fila de pedidos
- `GET /api/pedidos/{id}`: Busca pedido por ID
- `PUT /api/pedidos/{id}/status`: Atualiza status do pedido
- `POST /api/pedidos/{id}/checkout`: Realiza checkout
- `PATCH /api/pedidos/{id}/pronto`: Marca pedido como pronto
- `PATCH /api/pedidos/{id}/finalizar`: Finaliza pedido

---

## ⚙️ Configuração

As configurações principais estão no arquivo `application.yml`. Algumas variáveis de ambiente importantes:

- `SPRING_DATASOURCE_URL`: URL de conexão com o banco de dados
- `KAFKA_TOPIC_PEDIDO`: Tópico Kafka para envio de eventos de pedido criado
- `PRODUCAO_API_URL`: URL do serviço de Produção/Produtos
- `JWT_SECRET`: Chave secreta para assinatura de tokens JWT
