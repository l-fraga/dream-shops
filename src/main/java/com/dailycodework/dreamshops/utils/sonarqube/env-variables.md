# 🔧 Variáveis de Ambiente - SonarQube

Este documento lista todas as variáveis de ambiente disponíveis para configurar o SonarQube.

## 📋 Variáveis Obrigatórias

### Porta Externa
```env
# Porta externa do SonarQube
SONARQUBE_EXTERNAL_PORT=9000
```

## 🔧 Variáveis de Configuração Básica

### Banco de Dados
```env
# Configurações do banco de dados H2
SONAR_JDBC_USERNAME=sonar
SONAR_JDBC_PASSWORD=sonar
SONAR_JDBC_URL=jdbc:h2:tcp://localhost:9092/sonar
```

### Servidor Web
```env
# Configurações do servidor web
SONAR_WEB_HOST=0.0.0.0
SONAR_WEB_PORT=9000
SONAR_WEB_CONTEXT=/
SONAR_WEB_SESSION_TIMEOUT=60
SONAR_WEB_SESSION_COOKIE_SECURE=false
```

### Motor de Busca (Elasticsearch)
```env
# Configurações do Elasticsearch
SONAR_SEARCH_HOST=127.0.0.1
SONAR_SEARCH_PORT=9001
SONAR_SEARCH_JAVAOPTS=-Xmx512m -Xms512m -XX:MaxDirectMemorySize=256m -XX:+HeapDumpOnOutOfMemoryError
```

### Motor de Computação
```env
# Configurações do Compute Engine
SONAR_CE_JAVAOPTS=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError
SONAR_CE_WORKER_COUNT=1
SONAR_CE_GRACEFUL_STOP_TIMEOUT=4000
```

### Servidor Web (JVM)
```env
# Configurações JVM do servidor web
SONAR_WEB_JAVAOPTS=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError
```

## 🔒 Variáveis de Segurança

### Autenticação
```env
# Configurações de segurança
SONAR_FORCE_AUTHENTICATION=true
SONAR_SECURITY_REALM=default
SONAR_SECURITY_SOURCES_JAVASECURITY=true
SONAR_SECURITY_SOURCES_PYTHONSECURITY=true
```

## 📊 Variáveis de Logging

### Logs
```env
# Configurações de logging
SONAR_LOG_LEVEL=INFO
SONAR_LOG_USE_JSON=false
SONAR_LOG_MAX_FILES=10
SONAR_LOG_MAX_FILE_SIZE=10MB
SONAR_WEB_ACCESS_LOGS_ENABLE=false
SONAR_WEB_ACCESS_LOGS_PATTERN=combined
```

## 🗂️ Variáveis de Caminhos

### Diretórios
```env
# Configurações de caminhos
SONAR_PATH_DATA=/opt/sonarqube/data
SONAR_PATH_EXTENSIONS=/opt/sonarqube/extensions
SONAR_PATH_LOGS=/opt/sonarqube/logs
SONAR_PATH_TEMP=/opt/sonarqube/temp
SONAR_PATH_WEB=/opt/sonarqube/web
SONAR_PIDFILE=/opt/sonarqube/temp/sonar.pid
```

## 🎯 Variáveis do Projeto DreamShops

### Configurações Específicas
```env
# Configurações do projeto
SONAR_PROJECT_KEY=dream-shops
SONAR_PROJECT_NAME=DreamShops
SONAR_PROJECT_VERSION=1.0.0
SONAR_ORGANIZATION=dailycodework
```

## 🔧 Variáveis de Performance

### Configurações de Memória
```env
# Configurações de memória (já definidas no Dockerfile)
SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
SONAR_CE_JAVAOPTS=-Xmx512m -Xms128m
SONAR_WEB_JAVAOPTS=-Xmx512m -Xms128m
SONAR_SEARCH_JAVAOPTS=-Xmx512m -Xms512m
```

## 📧 Variáveis de Email (Opcional)

### Configuração SMTP
```env
# Configurações de email
SONAR_EMAIL_SMTP_HOST=smtp.gmail.com
SONAR_EMAIL_SMTP_PORT=587
SONAR_EMAIL_SMTP_USERNAME=seu-email@gmail.com
SONAR_EMAIL_SMTP_PASSWORD=sua-senha
SONAR_EMAIL_SMTP_SECURE_CONNECTION=false
SONAR_EMAIL_SMTP_FROM=noreply@dreamshops.com
```

## 🔗 Variáveis de Autenticação Externa (Opcional)

### LDAP
```env
# Configurações LDAP
SONAR_LDAP_URL=ldap://ldap.example.com:389
SONAR_LDAP_BIND_DN=cn=admin,dc=example,dc=com
SONAR_LDAP_BIND_PASSWORD=admin_password
SONAR_LDAP_USER_BASE_DN=ou=users,dc=example,dc=com
SONAR_LDAP_USER_REQUEST=(&(objectClass=person)(uid={login}))
SONAR_LDAP_GROUP_BASE_DN=ou=groups,dc=example,dc=com
SONAR_LDAP_GROUP_REQUEST=(&(objectClass=groupOfUniqueNames)(uniqueMember={dn}))
```

### GitHub
```env
# Configurações GitHub
SONAR_AUTH_GITHUB_CLIENT_ID=your_github_client_id
SONAR_AUTH_GITHUB_CLIENT_SECRET=your_github_client_secret
SONAR_AUTH_GITHUB_ORGANIZATIONS=your_organization
```

### GitLab
```env
# Configurações GitLab
SONAR_AUTH_GITLAB_APPLICATION_ID=your_gitlab_application_id
SONAR_AUTH_GITLAB_URL=https://gitlab.example.com
SONAR_AUTH_GITLAB_SECRET=your_gitlab_secret
```

### Azure DevOps
```env
# Configurações Azure DevOps
SONAR_AUTH_AZURE_CLIENT_ID=your_azure_client_id
SONAR_AUTH_AZURE_CLIENT_SECRET=your_azure_client_secret
SONAR_AUTH_AZURE_TENANT_ID=your_azure_tenant_id
```

### Bitbucket
```env
# Configurações Bitbucket
SONAR_AUTH_BITBUCKET_CLIENT_ID=your_bitbucket_client_id
SONAR_AUTH_BITBUCKET_CLIENT_SECRET=your_bitbucket_client_secret
SONAR_AUTH_BITBUCKET_URL=https://bitbucket.org
```

## 🌐 Variáveis de Proxy (Opcional)

### Configuração de Proxy
```env
# Configurações de proxy
SONAR_HTTP_PROXY_HOST=proxy.example.com
SONAR_HTTP_PROXY_PORT=8080
SONAR_HTTP_PROXY_USER=proxy_user
SONAR_HTTP_PROXY_PASSWORD=proxy_password
```

## 🔧 Variáveis de Cluster (Opcional)

### Configuração de Cluster
```env
# Configurações de cluster
SONAR_CLUSTER_ENABLED=false
SONAR_CLUSTER_NAME=dreamshops-cluster
SONAR_CLUSTER_NODE_NAME=node1
SONAR_CLUSTER_NODE_HOST=localhost
SONAR_CLUSTER_NODE_PORT=9001
```

## 📈 Variáveis de Desenvolvimento

### Modo de Desenvolvimento
```env
# Configurações de desenvolvimento
SONAR_DEV=false
SONAR_PROFILING_MODE=disabled
SONAR_UPDATECENTER_ACTIVATE=true
SONAR_WS_TIMEOUT=60
```

## 🎨 Variáveis Customizadas

### Propriedades Customizadas
```env
# Propriedades customizadas
SONAR_CUSTOM_PROPERTY1=
SONAR_CUSTOM_PROPERTY2=
SONAR_CUSTOM_PROPERTY3=
```

## 📝 Exemplo Completo do Arquivo .env

```env
# ============================================================================
# SONARQUBE CONFIGURATION
# ============================================================================

# Porta externa
SONARQUBE_EXTERNAL_PORT=9000

# Configurações básicas
SONAR_JDBC_USERNAME=sonar
SONAR_JDBC_PASSWORD=sonar
SONAR_JDBC_URL=jdbc:h2:tcp://localhost:9092/sonar

# Servidor web
SONAR_WEB_HOST=0.0.0.0
SONAR_WEB_PORT=9000
SONAR_WEB_CONTEXT=/
SONAR_WEB_SESSION_TIMEOUT=60
SONAR_WEB_SESSION_COOKIE_SECURE=false

# Elasticsearch
SONAR_SEARCH_HOST=127.0.0.1
SONAR_SEARCH_PORT=9001
SONAR_SEARCH_JAVAOPTS=-Xmx512m -Xms512m -XX:MaxDirectMemorySize=256m -XX:+HeapDumpOnOutOfMemoryError

# Compute Engine
SONAR_CE_JAVAOPTS=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError
SONAR_CE_WORKER_COUNT=1
SONAR_CE_GRACEFUL_STOP_TIMEOUT=4000

# Web Server JVM
SONAR_WEB_JAVAOPTS=-Xmx512m -Xms128m -XX:+HeapDumpOnOutOfMemoryError

# Segurança
SONAR_FORCE_AUTHENTICATION=true
SONAR_SECURITY_REALM=default
SONAR_SECURITY_SOURCES_JAVASECURITY=true
SONAR_SECURITY_SOURCES_PYTHONSECURITY=true

# Logging
SONAR_LOG_LEVEL=INFO
SONAR_LOG_USE_JSON=false
SONAR_LOG_MAX_FILES=10
SONAR_LOG_MAX_FILE_SIZE=10MB
SONAR_WEB_ACCESS_LOGS_ENABLE=false
SONAR_WEB_ACCESS_LOGS_PATTERN=combined

# Caminhos
SONAR_PATH_DATA=/opt/sonarqube/data
SONAR_PATH_EXTENSIONS=/opt/sonarqube/extensions
SONAR_PATH_LOGS=/opt/sonarqube/logs
SONAR_PATH_TEMP=/opt/sonarqube/temp
SONAR_PATH_WEB=/opt/sonarqube/web
SONAR_PIDFILE=/opt/sonarqube/temp/sonar.pid

# Projeto DreamShops
SONAR_PROJECT_KEY=dream-shops
SONAR_PROJECT_NAME=DreamShops
SONAR_PROJECT_VERSION=1.0.0
SONAR_ORGANIZATION=dailycodework

# Performance
SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true

# Desenvolvimento
SONAR_DEV=false
SONAR_PROFILING_MODE=disabled
SONAR_UPDATECENTER_ACTIVATE=true
SONAR_WS_TIMEOUT=60

# Cluster (desabilitado por padrão)
SONAR_CLUSTER_ENABLED=false
SONAR_CLUSTER_NAME=dreamshops-cluster
SONAR_CLUSTER_NODE_NAME=node1
SONAR_CLUSTER_NODE_HOST=localhost
SONAR_CLUSTER_NODE_PORT=9001

# Proxy (opcional)
# SONAR_HTTP_PROXY_HOST=
# SONAR_HTTP_PROXY_PORT=
# SONAR_HTTP_PROXY_USER=
# SONAR_HTTP_PROXY_PASSWORD=

# Email (opcional)
# SONAR_EMAIL_SMTP_HOST=
# SONAR_EMAIL_SMTP_PORT=
# SONAR_EMAIL_SMTP_USERNAME=
# SONAR_EMAIL_SMTP_PASSWORD=
# SONAR_EMAIL_SMTP_SECURE_CONNECTION=false
# SONAR_EMAIL_SMTP_FROM=

# LDAP (opcional)
# SONAR_LDAP_URL=
# SONAR_LDAP_BIND_DN=
# SONAR_LDAP_BIND_PASSWORD=
# SONAR_LDAP_USER_BASE_DN=
# SONAR_LDAP_USER_REQUEST=
# SONAR_LDAP_GROUP_BASE_DN=
# SONAR_LDAP_GROUP_REQUEST=

# GitHub (opcional)
# SONAR_AUTH_GITHUB_CLIENT_ID=
# SONAR_AUTH_GITHUB_CLIENT_SECRET=
# SONAR_AUTH_GITHUB_ORGANIZATIONS=

# GitLab (opcional)
# SONAR_AUTH_GITLAB_APPLICATION_ID=
# SONAR_AUTH_GITLAB_URL=
# SONAR_AUTH_GITLAB_SECRET=

# Azure DevOps (opcional)
# SONAR_AUTH_AZURE_CLIENT_ID=
# SONAR_AUTH_AZURE_CLIENT_SECRET=
# SONAR_AUTH_AZURE_TENANT_ID=

# Bitbucket (opcional)
# SONAR_AUTH_BITBUCKET_CLIENT_ID=
# SONAR_AUTH_BITBUCKET_CLIENT_SECRET=
# SONAR_AUTH_BITBUCKET_URL=

# Propriedades customizadas (opcional)
# SONAR_CUSTOM_PROPERTY1=
# SONAR_CUSTOM_PROPERTY2=
# SONAR_CUSTOM_PROPERTY3=
```

## 🚀 Como Usar

1. **Copie as variáveis necessárias** para seu arquivo `.env`
2. **Ajuste os valores** conforme sua necessidade
3. **Comente as variáveis opcionais** que não vai usar
4. **Reinicie o SonarQube** para aplicar as mudanças

## ⚠️ Observações Importantes

- **Variáveis obrigatórias**: Apenas `SONARQUBE_EXTERNAL_PORT` é obrigatória
- **Valores padrão**: Todas as outras variáveis têm valores padrão seguros
- **Segurança**: Nunca commite senhas ou tokens no controle de versão
- **Performance**: Ajuste as configurações de memória conforme seu ambiente
- **Logs**: Use `SONAR_LOG_LEVEL=DEBUG` para troubleshooting

## 🔧 Troubleshooting

### Verificar Variáveis
```bash
# Verificar variáveis carregadas
docker-compose exec sonarqube env | grep SONAR

# Verificar configuração
docker-compose exec sonarqube cat /opt/sonarqube/conf/sonar.properties
```

### Logs de Configuração
```bash
# Ver logs do SonarQube
docker-compose logs -f sonarqube

# Ver logs de inicialização
docker-compose logs sonarqube | grep -i "config"
``` 