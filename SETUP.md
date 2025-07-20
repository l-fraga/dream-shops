# Guia de Configuração e Teste - Dream Shops API

Este guia fornece instruções detalhadas para configurar, executar e testar a API Dream Shops.

## 📋 Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven
- Git
- Postman (opcional)

## 🚀 Configuração Inicial

### 1. Clone o Repositório
```bash
git clone <seu-repositorio>
cd dream-shops
```

### 2. Configure o Ambiente

Crie um arquivo `.env` na raiz do projeto:
```env
# PostgreSQL
POSTGRES_DB=dreamshops
POSTGRES_USER=dreamshops_user
POSTGRES_PASSWORD=dreamshops_password

# Spring Boot
SPRING_APPLICATION_NAME=dream-shops
SERVER_PORT=8080
API_PREFIX=/api/v1
SPRING_JPA_HIBERNATE_DDL_AUTO=update

# JWT
AUTH_TOKEN_JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
AUTH_TOKEN_EXPIRATION_IN_MILS=86400000
```

### 3. Crie os Volumes Docker
```bash
docker volume create dreamshops-postgres-data
docker volume create dreamshops-sonarqube-data
```

## 🏃‍♂️ Executando a Aplicação

### Usando Docker Compose (Recomendado)
```bash
# Construir e iniciar todos os serviços
docker-compose up -d

# Verificar logs
docker-compose logs -f app
```

### Execução Local
```bash
# Compilar
./mvnw clean install

# Executar
./mvnw spring-boot:run
```

## 🧪 Testando a API via Swagger UI

### 1. Acessando o Swagger
- Abra o navegador
- Acesse: http://localhost:8080/swagger-ui/index.html

### 2. Autenticação

#### 2.1 Usuários Padrão
A aplicação cria automaticamente os seguintes usuários:
- **Administrador**: 
  - Email: admin1@email.com
  - Senha: 123456
- **Usuário Regular**: 
  - Email: sam1@email.com
  - Senha: 123456

#### 2.2 Login
1. Localize o endpoint `/api/v1/auth/login`
2. Clique em "Try it out"
3. Insira as credenciais:
```json
{
  "email": "admin1@email.com",
  "password": "123456"
}
```
4. Execute e copie o token JWT da resposta

#### 2.3 Autorização
1. Clique no botão "Authorize" (🔒) no topo da página
2. No campo "Value", insira: `Bearer seu-token-jwt`
3. Clique em "Authorize"

### 3. Testando Endpoints

#### 3.1 Produtos (Requer ROLE_ADMIN)
1. **Listar Produtos**
   - GET `/api/v1/products/all`
   - Não requer autenticação

2. **Criar Produto**
   - POST `/api/v1/products/add`
   - Requer token ADMIN
   - Exemplo de payload:
```json
{
  "name": "Smartphone XYZ",
  "brand": "TechBrand",
  "price": 999.99,
  "inventory": 50,
  "description": "Smartphone top de linha",
  "category": {
    "name": "Eletrônicos"
  }
}
```

#### 3.2 Carrinho
1. **Adicionar ao Carrinho**
   - POST `/api/v1/cartItems/add`
   - Parâmetros:
     - productId: ID do produto
     - quantity: Quantidade

2. **Ver Carrinho**
   - GET `/api/v1/carts/{cartId}`

#### 3.3 Pedidos
1. **Criar Pedido**
   - POST `/api/v1/orders/order`
   - Requer carrinho com itens

2. **Listar Pedidos**
   - GET `/api/v1/orders/user/{userId}/order`

### 4. Fluxo de Teste Completo

1. **Autenticação**
   - Login como admin
   - Copiar token JWT

2. **Gestão de Produtos**
   - Criar categoria
   - Adicionar produto
   - Listar produtos

3. **Carrinho e Pedidos**
   - Adicionar item ao carrinho
   - Verificar carrinho
   - Criar pedido
   - Verificar status do pedido

## 📊 Monitoramento

### Prometheus
- Acesso: http://localhost:9090
- Métricas disponíveis em: http://localhost:8080/actuator/prometheus

### SonarQube
- Acesso: http://localhost:9000
- Credenciais padrão: admin/admin

## 🔍 Verificando Logs

```bash
# Logs da aplicação
docker-compose logs -f app

# Logs do banco de dados
docker-compose logs -f postgres

# Logs do SonarQube
docker-compose logs -f sonarqube
```

## ❗ Troubleshooting

### Problemas Comuns

1. **Erro de Conexão com Banco**
   - Verifique se o PostgreSQL está rodando
   - Confirme as credenciais no .env

2. **Erro 401 Unauthorized**
   - Verifique se o token JWT é válido
   - Confirme se está usando o prefixo "Bearer"

3. **Swagger não Carrega**
   - Verifique se a aplicação está rodando
   - Limpe o cache do navegador

4. **Erro ao Criar Produto**
   - Verifique se está autenticado como ADMIN
   - Confirme o formato do payload

## 🔄 Atualizando a Aplicação

```bash
# Parar os containers
docker-compose down

# Atualizar o código
git pull

# Reconstruir e iniciar
docker-compose up -d --build
``` 