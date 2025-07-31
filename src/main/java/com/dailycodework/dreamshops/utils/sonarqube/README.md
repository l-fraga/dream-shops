# 🔍 SonarQube - DreamShops

Este diretório contém a configuração completa do SonarQube para análise de qualidade de código do projeto DreamShops.

## 📁 Estrutura de Arquivos

```
src/main/java/com/dailycodework/dreamshops/utils/sonarqube/
├── Dockerfile                    # Imagem Docker personalizada
├── sonar.properties             # Configuração do SonarQube
├── sonar-project.properties     # Configuração do projeto
├── init-sonar.sh               # Script de inicialização
└── README.md                   # Esta documentação
```

## 🚀 Como Usar

### 1. **Configurar Variáveis de Ambiente**

Adicione ao seu arquivo `.env`:

```env
# ---------- SonarQube ----------
SONARQUBE_EXTERNAL_PORT=9000

# Configurações básicas (opcional - têm valores padrão)
SONAR_PROJECT_KEY=dream-shops
SONAR_PROJECT_NAME=DreamShops
SONAR_PROJECT_VERSION=1.0.0
SONAR_ORGANIZATION=dailycodework

# Configurações de memória (opcional)
SONAR_CE_JAVAOPTS=-Xmx512m -Xms128m
SONAR_WEB_JAVAOPTS=-Xmx512m -Xms128m
SONAR_SEARCH_JAVAOPTS=-Xmx512m -Xms512m

# Configurações de segurança (opcional)
SONAR_FORCE_AUTHENTICATION=true
SONAR_WEB_SESSION_TIMEOUT=60

# Configurações de logging (opcional)
SONAR_LOG_LEVEL=INFO
SONAR_LOG_USE_JSON=false
```

**📋 Nota**: Apenas `SONARQUBE_EXTERNAL_PORT` é obrigatória. Todas as outras variáveis têm valores padrão seguros. Veja `env-variables.md` para a lista completa de variáveis disponíveis.

### 2. **Criar Volume Docker**

```bash
docker volume create dreamshops-sonarqube-data
```

### 3. **Iniciar SonarQube**

```bash
# Iniciar apenas o SonarQube
docker-compose up -d sonarqube

# Ou iniciar todos os serviços
docker-compose up -d
```

### 4. **Acessar SonarQube**

- **URL**: http://localhost:9000
- **Usuário**: admin
- **Senha**: admin

### 5. **Executar Análise de Código**

```bash
# Gerar relatório de cobertura
mvn clean test jacoco:report

# Executar análise SonarQube
mvn sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=admin \
  -Dsonar.password=admin
```

## 📊 Métricas Analisadas

### Qualidade de Código
- **Bugs**: Problemas que podem causar falhas
- **Vulnerabilidades**: Problemas de segurança
- **Code Smells**: Problemas de manutenibilidade
- **Duplicações**: Código duplicado

### Cobertura de Testes
- **Cobertura de Linhas**: Porcentagem de linhas testadas
- **Cobertura de Branches**: Porcentagem de branches testados
- **Cobertura de Funções**: Porcentagem de funções testadas

### Complexidade
- **Complexidade Ciclomática**: Medida de complexidade do código
- **Complexidade Cognitiva**: Medida de dificuldade de entendimento

## 🎯 Configurações do Projeto

### Exclusões
- **DTOs**: Classes de transferência de dados
- **Requests/Responses**: Classes de requisição/resposta
- **Configs**: Classes de configuração
- **Utils**: Classes utilitárias
- **Models**: Entidades do banco de dados
- **Repositories**: Interfaces de repositório

### Inclusões
- **Services**: Lógica de negócio
- **Controllers**: Controladores da API
- **Security**: Configurações de segurança

## 📈 Quality Gates

### Critérios de Qualidade
- **Cobertura de Código**: > 80%
- **Duplicação**: < 3%
- **Vulnerabilidades**: 0
- **Bugs**: < 10
- **Code Smells**: < 100

### Configuração de Quality Gate
1. Acesse: http://localhost:9000
2. Vá em: Administration > Quality Gates
3. Configure os critérios acima

## 🔧 Troubleshooting

### Problemas Comuns

1. **SonarQube não inicia**
   - Verificar se a porta 9000 está livre
   - Verificar logs: `docker-compose logs sonarqube`

2. **Análise falha**
   - Verificar se o projeto foi criado no SonarQube
   - Verificar se o token está correto
   - Verificar se os relatórios foram gerados

3. **Cobertura não aparece**
   - Executar: `mvn clean test jacoco:report`
   - Verificar se o arquivo `target/site/jacoco/jacoco.xml` existe

4. **Erro de memória**
   - Aumentar memória no Dockerfile
   - Verificar se há RAM suficiente

## 📋 Comandos Úteis

### Verificar Status
```bash
# Status dos containers
docker-compose ps

# Logs do SonarQube
docker-compose logs -f sonarqube

# Verificar se está respondendo
curl http://localhost:9000/api/system/status
```

### Limpar Dados
```bash
# Parar SonarQube
docker-compose stop sonarqube

# Remover volume
docker volume rm dreamshops-sonarqube-data

# Reiniciar
docker-compose up -d sonarqube
```

### Backup e Restore
```bash
# Backup
docker exec sonarqube tar czf /opt/sonarqube/data/backup.tar.gz /opt/sonarqube/data

# Restore
docker exec sonarqube tar xzf /opt/sonarqube/data/backup.tar.gz -C /
```

## 🎨 Integração com CI/CD

### GitHub Actions
```yaml
- name: SonarQube Analysis
  run: |
    mvn clean test jacoco:report
    mvn sonar:sonar \
      -Dsonar.host.url=${{ secrets.SONAR_HOST_URL }} \
      -Dsonar.login=${{ secrets.SONAR_TOKEN }}
```

### Jenkins Pipeline
```groovy
stage('SonarQube Analysis') {
    steps {
        sh 'mvn clean test jacoco:report'
        sh 'mvn sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dsonar.login=admin -Dsonar.password=admin'
    }
}
```

## 📊 Relatórios

### Relatórios Disponíveis
- **Dashboard**: Visão geral do projeto
- **Issues**: Problemas encontrados
- **Measures**: Métricas detalhadas
- **Security Hotspots**: Pontos de segurança
- **Code Coverage**: Cobertura de testes

### Exportação de Relatórios
- **PDF**: Relatórios em PDF
- **CSV**: Dados em formato CSV
- **API**: Dados via API REST

## 🔍 Configurações Avançadas

### Variáveis de Ambiente Opcionais
```env
# Configurações de memória (já definidas no Dockerfile)
SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
SONAR_CE_JAVAOPTS=-Xmx512m -Xms128m
SONAR_WEB_JAVAOPTS=-Xmx512m -Xms128m
SONAR_SEARCH_JAVAOPTS=-Xmx512m -Xms512m

# Configurações de segurança
SONAR_WEB_SESSION_TIMEOUT=60
SONAR_WEB_SESSION_COOKIE_SECURE=false

# Configurações de email (opcional)
SONAR_EMAIL_SMTP_HOST=smtp.gmail.com
SONAR_EMAIL_SMTP_PORT=587
SONAR_EMAIL_SMTP_USERNAME=seu-email@gmail.com
SONAR_EMAIL_SMTP_PASSWORD=sua-senha
```

### Configurações de Performance
```properties
# Configurações de memória
sonar.ce.javaOpts=-Xmx512m -Xms128m
sonar.web.javaOpts=-Xmx512m -Xms128m
sonar.search.javaOpts=-Xmx512m -Xms512m

# Configurações de worker
sonar.ce.workerCount=1
sonar.ce.gracefulStopTimeoutInMs=4000
```

## 🎯 Próximos Passos

1. **Configurar Quality Gates**
   - Definir critérios de qualidade
   - Configurar alertas

2. **Integrar com CI/CD**
   - Configurar pipelines
   - Automatizar análises

3. **Configurar Plugins**
   - Instalar plugins específicos
   - Configurar regras personalizadas

4. **Monitoramento**
   - Configurar alertas
   - Monitorar métricas

---

**Lembre-se**: O SonarQube é uma ferramenta poderosa para manter a qualidade do código. Use regularmente para identificar e corrigir problemas antes que se tornem críticos. 