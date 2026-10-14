# 🛒 Loja Online com Microserviços

Backend de uma loja online dividido em **4 microserviços independentes**, cada um com a sua API REST. Corre em containers Docker e inclui ficheiros de deploy para Kubernetes.

> Projeto desenvolvido no Mestrado em Engenharia Informática (ESTG, Politécnico do Porto), na unidade curricular de Microserviços.

## 🧩 Arquitetura

| Serviço | Porta | Responsabilidade |
|---|---|---|
| `identity-service` | 8080 | Registo de clientes e autenticação com **JWT** (Spring Security) |
| `catalog-service` | 8081 | Produtos e stock (inventário) |
| `order-service` | 8082 | Encomendas; comunica com os outros serviços via **WebClient** |
| `notification-service` | 8083 | Envios e notificações de encomenda |

Todos os serviços usam **PostgreSQL** com Spring Data JPA, organizados em camadas: `controller` → `service` → `repository` → `entity`/`dto`.

```
Cliente ──► identity-service  (login / JWT)
        ──► catalog-service   (produtos, stock)
        ──► order-service ──► catalog-service
                         └──► notification-service
                 │
             PostgreSQL
```

## 🛠 Tecnologias

Java · Spring Boot · Spring Security · JWT · Spring Data JPA · PostgreSQL · Maven · Lombok · ModelMapper · Docker / Docker Compose · Kubernetes

## ▶️ Como executar

**Com Docker Compose** (recomendado):

```bash
mvn clean package -DskipTests
docker compose up --build
```

**Com Kubernetes:**

```bash
kubectl apply -f k8s/
```

Os comandos usados durante o desenvolvimento estão no ficheiro [`Comandos`](Comandos).

## 🗂 Estrutura

```
├── identity-service/
├── catalog-service/
├── order-service/
├── notification-service/
├── k8s/                 # Manifests Kubernetes (um por serviço + PostgreSQL)
├── docker-compose.yml
└── pom.xml              # Projeto Maven multi-módulo
```

## 🔜 Próximos passos

- Comunicação assíncrona entre serviços com RabbitMQ
- API Gateway e Service Discovery (Spring Cloud)
- Credenciais da base de dados em variáveis de ambiente / Secrets
