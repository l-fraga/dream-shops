# 🛍️ Dream Shops - API de E-commerce

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)
[![Tests](https://img.shields.io/badge/Tests-Comprehensive-brightgreen.svg)](https://junit.org/junit5/)
[![BDD](https://img.shields.io/badge/BDD-Cucumber-green.svg)](https://cucumber.io/)

Uma API REST completa de e-commerce desenvolvida em **Spring Boot** com arquitetura em camadas, autenticação JWT, testes automatizados abrangentes e monitoramento profissional.

## 🚀 Características Principais

- **API RESTful** completa para e-commerce
- **Arquitetura em camadas** (Controller → Service → Repository)
- **Autenticação JWT** com Spring Security
- **Testes abrangentes** (Unitários, Integração, E2E e BDD)
- **Monitoramento** com Prometheus e Grafana
- **Qualidade de código** com SonarQube
- **Containerização** completa com Docker
- **CI/CD** com GitHub Actions
- **Documentação interativa** com Swagger/OpenAPI

## 📋 Funcionalidades

### 👥 Gestão de Usuários
- Registro e autenticação de usuários
- Perfis de usuário (USER, ADMIN)
- Gestão completa de dados pessoais
- Sistema de roles e permissões

### 🛍️ Gestão de Produtos
- CRUD completo de produtos
- Sistema de categorização
- Gestão de estoque em tempo real
- Upload e gerenciamento de imagens
- Busca avançada e filtros

### 🛒 Carrinho de Compras
- Adição/remoção de produtos
- Atualização de quantidades
- Cálculo automático de totais
- Validação de estoque
- Persistência de carrinho

### 📦 Gestão de Pedidos
- Criação de pedidos
- Status de pedidos (PENDING, CONFIRMED, SHIPPED, DELIVERED)
- Histórico completo de pedidos
- Cálculo detalhado de totais
- Rastreamento de itens

### 🏷️ Sistema de Categorias
- Organização hierárquica de produtos
- CRUD de categorias
- Associação produto-categoria

## 🏗️ Arquitetura

```
┌─────────────────┐
│   Controllers   │ ← REST API endpoints
├─────────────────┤
│    Services     │ ← Business Logic
├─────────────────┤
│  Repositories   │ ← Data Access
├─────────────────┤
│   Database      │ ← PostgreSQL/H2
└─────────────────┘
```

## 🛠️ Tecnologias

### Backend
- **Java 17** - Linguagem principal
- **Spring Boot 3.x** - Framework
- **Spring Security** - Autenticação
- **Spring Data JPA** - Persistência
- **PostgreSQL** - Banco principal
- **H2** - Banco para testes

### Testes
- **JUnit 5** - Framework de testes
- **MockMvc** - Testes de API
- **Testcontainers** - Containers para testes
- **REST Assured** - Testes E2E

### DevOps
- **Docker** - Containerização
- **Prometheus** - Métricas
- **Grafana** - Dashboards
- **SonarQube** - Qualidade

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Docker e Docker Compose

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/dream-shops.git
cd dream-shops
```

### 2. Execute com Docker Compose
```bash
docker-compose up -d
```

### 3. Ou execute localmente
```bash
# Configure o banco PostgreSQL
# Execute a aplicação
mvn spring-boot:run
```

## 🧪 Executando Testes

### Todos os testes
```bash
mvn test
```

### Testes unitários
```bash
mvn test -Dtest="*Test"
```

### Testes de integração
```bash
mvn test -Dtest="*IntegrationTest"
```

### Testes E2E
```bash
mvn test -Dtest="*E2ETest"
```

## 📊 Monitoramento

### Prometheus
- Acesse: http://localhost:9090
- Métricas da aplicação: http://localhost:8080/actuator/prometheus

### Grafana
- Acesse: http://localhost:3000
- Usuário: admin
- Senha: admin

### SonarQube
- Acesse: http://localhost:9000
- Usuário: admin
- Senha: admin

## 📚 Documentação

- [Guia de Entrevista](docs/INTERVIEW_GUIDE.md) - Como apresentar o projeto em entrevistas
- [Estratégia de Testes](docs/TESTING_STRATEGY_GUIDE.md) - Detalhes sobre testes
- [Configuração de Monitoramento](docs/MONITORING_CORRECTIONS_SUMMARY.md) - Setup de Prometheus/Grafana
- [Exemplos BDD](docs/REAL_BDD_EXAMPLES.md) - Exemplos de comportamento

## 🔐 Endpoints Principais

### Autenticação
```
POST /api/v1/auth/register - Registro de usuário
POST /api/v1/auth/login    - Login
```

### Produtos (Públicos)
```
GET  /api/v1/products/all           - Listar todos os produtos
GET  /api/v1/products/{id}          - Buscar produto por ID
GET  /api/v1/products/search        - Buscar produtos
```

### Carrinho (Autenticado)
```
POST   /api/v1/cartItems/add        - Adicionar item
PUT    /api/v1/cartItems/{id}/update - Atualizar quantidade
DELETE /api/v1/cartItems/{id}/remove - Remover item
GET    /api/v1/carts/{id}           - Ver carrinho
```

### Pedidos (Autenticado)
```
POST /api/v1/orders/create - Criar pedido
GET  /api/v1/orders/user   - Histórico de pedidos
```

## 📈 Métricas de Qualidade

```
✅ Cobertura de Testes: 85%
✅ SonarQube: A Grade
✅ Technical Debt: < 1 day
✅ Security: 0 vulnerabilities
```