# syntax=docker/dockerfile:1.6
# -------------------------------------------------
# STAGE 0 – argumentos reutilizáveis
# -------------------------------------------------
ARG MAVEN_VERSION=3.9.7
ARG JDK_IMAGE=eclipse-temurin:21-jdk
ARG JRE_IMAGE=eclipse-temurin:21-jre

# -------------------------------------------------
# STAGE 1 – build (usa Maven + JDK 21)
# -------------------------------------------------
FROM ${JDK_IMAGE} AS builder
ARG MAVEN_VERSION

# 1. Instala Maven (caso não use o Maven Wrapper)
RUN wget -qO- https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
      | tar xz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn

WORKDIR /workspace

# 2. Copia arquivos que raramente mudam (pom.xml) para cachear dependências
COPY pom.xml mvnw* ./
COPY .mvn/ .mvn/

# Baixa dependências sem copiar código-fonte ainda
RUN mvn -B -ntp dependency:go-offline

# 3. Agora sim copia o restante do código e compila
COPY src ./src
RUN mvn -B -ntp clean package -DskipTests -Dmaven.test.skip=true -Djacoco.skip=true

# -------------------------------------------------
# STAGE 2 – runtime (imagem final enxuta)
# -------------------------------------------------
FROM ${JRE_IMAGE} AS runtime
WORKDIR /app

# Copia apenas o JAR gerado
COPY --from=builder /workspace/target/*.jar app.jar

# Expoe portas padrão
EXPOSE 8080

# Ajustes de memória da JVM dentro do container
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"] 