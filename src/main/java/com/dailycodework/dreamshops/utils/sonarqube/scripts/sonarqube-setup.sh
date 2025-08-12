#!/bin/bash

echo "========================================"
echo "   SonarQube Setup - Dream Shops"
echo "========================================"

echo "[1/5] Criando volumes Docker..."
docker volume create dreamshops-sonarqube-data
docker volume create dreamshops-sonarqube-extensions
docker volume create dreamshops-sonarqube-logs

echo "[2/5] Verificando se PostgreSQL está rodando..."
if ! docker-compose ps postgres | grep -q "Up"; then
    echo "Iniciando PostgreSQL..."
    docker-compose up -d postgres
    sleep 30
fi

echo "[3/5] Criando banco de dados SonarQube..."
docker-compose exec postgres psql -U dreamshops_user -d dreamshops -c "CREATE DATABASE IF NOT EXISTS sonar;"
docker-compose exec postgres psql -U dreamshops_user -d dreamshops -c "CREATE USER IF NOT EXISTS sonar WITH PASSWORD 'sonar';"
docker-compose exec postgres psql -U dreamshops_user -d dreamshops -c "GRANT ALL PRIVILEGES ON DATABASE sonar TO sonar;"

echo "[4/5] Iniciando SonarQube..."
docker-compose up -d sonarqube

echo "[5/5] Aguardando SonarQube inicializar..."
sleep 60

echo "========================================"
echo "   Setup concluído!"
echo "   Acesse: http://localhost:9000"
echo "   Usuário: admin"
echo "   Senha: admin (primeira vez)"
echo "========================================"