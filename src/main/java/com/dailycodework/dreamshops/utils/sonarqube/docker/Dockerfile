# Dockerfile personalizado para SonarQube - Dream Shops
FROM sonarqube:latest

# Metadados
LABEL maintainer="Dream Shops Team"
LABEL description="SonarQube customizado para o projeto Dream Shops"
LABEL version="1.0"

ENV SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true

# Criar diretórios necessários
RUN mkdir -p /opt/sonarqube/extensions/plugins

# Não instalar plugins manualmente, usar o Marketplace do SonarQube
WORKDIR /opt/sonarqube/extensions/plugins/tmp
RUN curl -L -O https://github.com/checkstyle/sonar-checkstyle/releases/download/10.9.3/checkstyle-sonar-plugin-10.9.3.jar && \
    curl -L -O https://github.com/spotbugs/sonar-findbugs/releases/download/4.2.3/sonar-findbugs-plugin-4.2.3.jar && \
    mv *.jar /opt/sonarqube/extensions/plugins/ && \
    rm -rf /opt/sonarqube/extensions/plugins/tmp

# Copiar configuração personalizada
COPY conf/sonar.properties /opt/sonarqube/conf/sonar.properties

# Definir usuário não-root para segurança
USER sonarqube

# Expor porta
EXPOSE 9000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=5 \
    CMD curl -f http://localhost:9000/api/system/status || exit 1

# Ponto de entrada
ENTRYPOINT ["./bin/run.sh"]