# Configuração do Prometheus para DreamShops

Este diretório contém a configuração do Prometheus para monitoramento da aplicação DreamShops.

## Arquivos de Configuração

- `prometheus.yml`: Configuração principal do Prometheus
- `alerts.yml`: Regras de alerta personalizadas
- `Dockerfile`: Dockerfile para construir a imagem do Prometheus

## Serviços Monitorados

1. **Aplicação Spring Boot** (`dreamshops-app`)
   - Endpoint: `http://app:8080/actuator/prometheus`
   - Métricas: JVM, HTTP, HikariCP, etc.

2. **PostgreSQL Exporter** (`postgresql-exporter`)
   - Endpoint: `http://postgresql-exporter:9187/metrics`
   - Métricas: Conexões, queries, performance do banco

3. **PostgreSQL Database** (`postgres`)
   - Monitorado via postgresql-exporter

## Alertas Configurados

- **ServiceDown**: Quando um serviço está offline
- **HighResponseTime**: Tempo de resposta alto (>2s)
- **HighMemoryUsage**: Uso de memória alto (>80%)
- **HighCPUUsage**: Uso de CPU alto (>80%)
- **HighDatabaseConnections**: Muitas conexões de banco (>80%)
- **HighErrorRate**: Taxa de erro alta (5xx)
- **FrequentGarbageCollection**: GC muito frequente
- **LongGarbageCollection**: GC demorado (>1s)

## Como Usar

1. **Criar volumes externos**:
   ```bash
   docker volume create dreamshops-prometheus-data
   ```

2. **Configurar variáveis de ambiente**:
   - Verifique o arquivo `ENV_VARIABLES.md` na raiz do projeto

3. **Iniciar os serviços**:
   ```bash
   docker-compose up -d
   ```

4. **Acessar o Prometheus**:
   - URL: `http://localhost:9090`
   - Targets: `http://localhost:9090/targets`
   - Alerts: `http://localhost:9090/alerts`

## Métricas Importantes

### Aplicação Spring Boot
- `http_server_requests_seconds_count`: Total de requisições
- `http_server_requests_seconds_max`: Tempo máximo de resposta
- `jvm_memory_used_bytes`: Memória utilizada
- `jvm_memory_max_bytes`: Memória máxima
- `hikaricp_connections_active`: Conexões ativas do banco
- `hikaricp_connections_max`: Conexões máximas do banco

### PostgreSQL
- `pg_stat_database_connections`: Conexões ativas
- `pg_stat_database_tup_fetched`: Tuplas buscadas
- `pg_stat_database_tup_inserted`: Tuplas inseridas
- `pg_stat_database_tup_updated`: Tuplas atualizadas
- `pg_stat_database_tup_deleted`: Tuplas deletadas

## Troubleshooting

1. **Prometheus não consegue acessar a aplicação**:
   - Verifique se a aplicação está rodando: `docker-compose ps`
   - Teste o endpoint: `curl http://localhost:8080/actuator/prometheus`

2. **Métricas não aparecem**:
   - Verifique se o Micrometer está configurado no `pom.xml`
   - Confirme se o endpoint `/actuator/prometheus` está habilitado

3. **Alertas não funcionam**:
   - Verifique se o arquivo `alerts.yml` está sendo carregado
   - Confirme se as expressões PromQL estão corretas 