# 🛍️ Dream Shops - API de E-commerce

Este projeto implementa uma API completa de e-commerce utilizando Spring Boot 3.4.3 com Java 21. A aplicação permite:

- ✅ Gestão completa de produtos e categorias
- ✅ Sistema de carrinho de compras
- ✅ Gestão de pedidos e status
- ✅ Upload e gestão de imagens
- ✅ Sistema de usuários
- ✅ Documentação automática via Postman Collection
- ✅ Containerização com Docker

---

## 📚 Documentação do Projeto

### 📋 Documentação Geral
- [**README-Postman-Collection.md**](./README-Postman-Collection.md) - Collection completa do Postman com todos os endpoints
- [**Dream-Shops-API.postman_collection.json**](./Dream-Shops-API.postman_collection.json) - Arquivo da collection do Postman

### 🏗️ Documentação dos Serviços

#### Backend Services (Java/Spring Boot)
- **User Management** - Gestão de usuários e autenticação
- **Product Management** - Gestão de produtos, categorias e imagens
- **Cart Management** - Sistema de carrinho de compras
- **Order Management** - Gestão de pedidos e status
- **Image Management** - Upload e gestão de imagens de produtos

---

## 📁 Estrutura do Projeto

```
dream-shops/
├── docker-compose.yml                  # Orquestra PostgreSQL e Spring Boot
├── Dockerfile                          # Dockerfile para aplicação Spring Boot
├── .env                                # Variáveis de ambiente
├── pom.xml                             # Configuração Maven
├── README-Postman-Collection.md        # Documentação Postman
├── Dream-Shops-API.postman_collection.json # Collection Postman
└── src/
    └── main/
        ├── java/
        │   └── com/dailycodework/dreamshops/
        │       ├── DreamShopsApplication.java    # Classe principal
        │       ├── config/
        │       │   └── ShopConfig.java           # Configurações do ModelMapper
        │       ├── controller/                   # Controllers REST
        │       │   ├── UserController.java
        │       │   ├── ProductController.java
        │       │   ├── CartController.java
        │       │   ├── CartItemController.java
        │       │   ├── OrderController.java
        │       │   ├── CategoryController.java
        │       │   └── ImageController.java
        │       ├── service/                      # Lógica de negócio
        │       │   ├── user/
        │       │   ├── product/
        │       │   ├── cart/
        │       │   ├── order/
        │       │   ├── category/
        │       │   └── image/
        │       ├── repository/                   # Repositórios JPA
        │       ├── model/                        # Entidades JPA
        │       ├── dto/                          # Objetos de transferência
        │       ├── request/                      # Objetos de requisição
        │       ├── response/                     # Objetos de resposta
        │       ├── enums/                        # Enumerações
        │       └── exceptions/                   # Exceções personalizadas
        └── resources/
            └── application.properties            # Configurações da aplicação
```

---

## 🛠️ Requisitos

- **Java 21** (JDK)
- **Maven 3.9.7+**
- **Docker e Docker Compose**
- **PostgreSQL 16** (via Docker)
- **Postman** (para testes da API)

---

## 🚀 Configuração Inicial

### 1. Clone o repositório:

```sh
git clone https://github.com/l-fraga/dream-shops.git
cd dream-shops
```

### 2. Configure as variáveis de ambiente:

Crie um arquivo `.env` na raiz do projeto:

```env
# PostgreSQL
POSTGRES_DB=dreamshops
POSTGRES_USER=dreamshops_user
POSTGRES_PASSWORD=dreamshops_password
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/dreamshops
SPRING_DATASOURCE_USERNAME=dreamshops_user
SPRING_DATASOURCE_PASSWORD=dreamshops_password

# Spring Boot
SPRING_APPLICATION_NAME=dream-shops
SERVER_PORT=8080
API_PREFIX=/api/v1
SPRING_JPA_HIBERNATE_DDL_AUTO=update

# Upload
SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=5MB
SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=5MB
```

### 3. Crie o volume Docker para PostgreSQL:

```sh
docker volume create dreamshops-postgres-data
```

---

## 🚦 Executar o projeto

### Executar com Docker Compose (Recomendado):

```sh
# Construir e subir todos os containers
docker-compose up -d --build

# Verificar status dos containers
docker ps

# Ver logs da aplicação
docker logs dreamshops-app

# Parar os containers
docker-compose down
```

### Executar localmente (desenvolvimento):

```sh
# Instalar dependências
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

---

## 📖 Documentação da API

### Endpoints Principais

#### 👤 Usuários (`/api/v1/users`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/users/{userId}/user` | Buscar usuário por ID |
| `POST` | `/users/add` | Criar novo usuário |
| `PUT` | `/users/{userId}/update` | Atualizar usuário |
| `DELETE` | `/users/{userId}/delete` | Deletar usuário |

#### 🛍️ Produtos (`/api/v1/products`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/products/all` | Listar todos os produtos |
| `GET` | `/products/product/{productId}/product` | Buscar produto por ID |
| `POST` | `/products/add` | Adicionar novo produto |
| `PUT` | `/products/product/{productId}/update` | Atualizar produto |
| `DELETE` | `/products/product/{productId}/delete` | Deletar produto |
| `GET` | `/products/products/{name}/products` | Buscar produtos por nome |
| `GET` | `/products/product/{category}/all/products` | Buscar produtos por categoria |

#### 🛒 Carrinho (`/api/v1/carts`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/carts/{cartId}/my-cart` | Buscar carrinho |
| `DELETE` | `/carts/{cartId}/clear` | Limpar carrinho |
| `GET` | `/carts/{cartId}/cart/total-price` | Calcular total |

#### 📦 Pedidos (`/api/v1/orders`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/orders/order/create` | Criar novo pedido |
| `GET` | `/orders/{orderId}/order` | Buscar pedido por ID |
| `GET` | `/orders/{userId}/orders` | Listar pedidos do usuário |

---

## 🔑 Exemplos de Uso via cURL

### Criar usuário:

```sh
curl -X POST http://localhost:8080/api/v1/users/add \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao.silva@exemplo.com",
    "password": "MinhaSenha123"
  }'
```

### Adicionar produto:

```sh
curl -X POST http://localhost:8080/api/v1/products/add \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15",
    "brand": "Apple",
    "price": 999.99,
    "inventory": 50,
    "description": "Último modelo do iPhone",
    "category": {
      "name": "Eletrônicos"
    }
  }'
```

### Adicionar item ao carrinho:

```sh
curl -X POST "http://localhost:8080/api/v1/cartItems/item/add?cartId=1&productId=1&quantity=2"
```

### Criar pedido:

```sh
curl -X POST "http://localhost:8080/api/v1/orders/order/create?userId=1"
```

---

## 🧪 Testes com Postman

### 1. Importar Collection:
1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `Dream-Shops-API.postman_collection.json`

### 2. Configurar Environment:
Configure as seguintes variáveis no Postman:
- `baseUrl`: `http://localhost:8080/api/v1`
- `userId`: `1`
- `productId`: `1`
- `cartId`: `1`
- `orderId`: `1`

### 3. Fluxo de Testes:
1. **Criar usuário** → `POST /users/add`
2. **Criar categoria** → `POST /categories/add`
3. **Adicionar produto** → `POST /products/add`
4. **Adicionar ao carrinho** → `POST /cartItems/item/add`
5. **Criar pedido** → `POST /orders/order/create`

---

## 🐛 Troubleshooting

### Problemas Comuns:

#### 1. Container não inicia:
```sh
# Verificar logs
docker logs dreamshops-app

# Verificar se PostgreSQL está rodando
docker logs dreamshops-db
```

#### 2. Erro de conexão com banco:
- Verifique se as variáveis de ambiente estão corretas
- Confirme se o volume `dreamshops-postgres-data` existe

#### 3. Health Check falha:
```sh
# Verificar health check
curl http://localhost:8080/actuator/health

# Verificar se aplicação está rodando
curl http://localhost:8080/api/v1/products/all
```

#### 4. Porta já em uso:
```sh
# Verificar portas em uso
netstat -ano | findstr :8080

# Parar containers conflitantes
docker-compose down
```

---

## 📊 Monitoramento

### Health Checks:
- **Aplicação**: `http://localhost:8080/actuator/health`
- **PostgreSQL**: Verificado automaticamente pelo Docker

### Logs:
```sh
# Logs da aplicação
docker logs -f dreamshops-app

# Logs do banco
docker logs -f dreamshops-db
```

### Métricas:
- **Porta da aplicação**: 8080
- **Porta de debug**: 5005
- **Porta do PostgreSQL**: 5432

---

## ✅ Melhores Práticas

### 🔒 Segurança:
- ✅ Sempre use variáveis de ambiente para dados sensíveis
- ✅ Nunca commite arquivos `.env` no Git
- ✅ Use HTTPS em produção
- ⚠️ **PENDENTE**: Implementar autenticação JWT
- ⚠️ **PENDENTE**: Criptografar senhas

### 🏗️ Arquitetura:
- ✅ Separação clara de responsabilidades
- ✅ Uso de DTOs para transferência de dados
- ✅ Tratamento de exceções personalizado
- ✅ Configuração via propriedades

### 🧪 Testes:
- ⚠️ **PENDENTE**: Implementar testes unitários
- ⚠️ **PENDENTE**: Implementar testes de integração
- ✅ Collection Postman completa

---

## 📌 Commits

Padrão sugerido para commits:

```sh
git commit -m "feat(contexto): breve descrição da funcionalidade implementada"
```

Exemplos:
```sh
git commit -m "feat(products): implementar busca de produtos por categoria"
git commit -m "fix(users): corrigir bug na criação de usuários"
git commit -m "docs(api): atualizar documentação do Postman"
git commit -m "chore(docker): otimizar Dockerfile para produção"
```

---

## 🚀 Roadmap

### ✅ Implementado:
- [x] CRUD de usuários
- [x] CRUD de produtos
- [x] Sistema de carrinho
- [x] Gestão de pedidos
- [x] Upload de imagens
- [x] Containerização Docker
- [x] Documentação Postman

### 🔄 Em Desenvolvimento:
- [ ] Autenticação JWT
- [ ] Criptografia de senhas
- [ ] Validações de entrada
- [ ] Testes unitários

### 📋 Planejado:
- [ ] Sistema de pagamentos
- [ ] Notificações por email
- [ ] Dashboard administrativo
- [ ] API de relatórios
- [ ] Cache Redis
- [ ] Monitoramento com Prometheus

---

## 📚 Recursos Adicionais

- [Spring Boot - Documentação Oficial](https://spring.io/projects/spring-boot)
- [Spring Data JPA - Guia](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL - Documentação](https://www.postgresql.org/docs/)
- [Docker - Guia](https://docs.docker.com/)
- [Postman - Documentação](https://learning.postman.com/)

---

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Dream Shops Team**
- Email: contato@dreamshops.com
- GitHub: [@dreamshops](https://github.com/dreamshops)

---

## 🙏 Agradecimentos

- Spring Boot Team
- PostgreSQL Community
- Docker Team
- Postman Team 