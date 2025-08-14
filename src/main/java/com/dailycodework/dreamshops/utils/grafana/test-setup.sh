#!/bin/bash

# 🧪 Test Script - Grafana Setup DreamShops
echo "🧪 Testing Grafana Setup for DreamShops..."

echo ""
echo "📋 Checking prerequisites..."

# Check if docker-compose.yml exists
if [ ! -f "docker-compose.yml" ]; then
    echo "❌ docker-compose.yml not found"
    exit 1
fi
echo "✅ docker-compose.yml found"

# Check if .env exists
if [ ! -f ".env" ]; then
    echo "⚠️  .env file not found, using env.example"
    if [ -f "env.example" ]; then
        cp env.example .env
        echo "✅ .env created from env.example"
    else
        echo "❌ env.example not found"
        exit 1
    fi
else
    echo "✅ .env file found"
fi

echo ""
echo "🗄️  Creating Grafana volume..."
docker volume create dreamshops-grafana-data
echo "✅ Volume created"

echo ""
echo "🚀 Starting Grafana service..."
docker-compose up -d grafana

echo ""
echo "⏳ Waiting for Grafana to be ready..."
sleep 30

echo ""
echo "🔍 Testing services..."

# Test Grafana
echo "Testing Grafana..."
if curl -f -s http://localhost:3000/api/health > /dev/null; then
    echo "✅ Grafana is healthy"
else
    echo "❌ Grafana is not responding"
    echo "📋 Grafana logs:"
    docker-compose logs --tail=10 grafana
fi

# Test Prometheus (if running)
echo "Testing Prometheus..."
if curl -f -s http://localhost:9090/-/healthy > /dev/null; then
    echo "✅ Prometheus is healthy"
else
    echo "⚠️  Prometheus is not running (optional for Grafana test)"
fi

# Test Application (if running)
echo "Testing Application..."
if curl -f -s http://localhost:8080/actuator/health > /dev/null; then
    echo "✅ DreamShops application is healthy"
else
    echo "⚠️  DreamShops application is not running (optional for Grafana test)"
fi

echo ""
echo "📊 Grafana Information:"
echo "🌐 URL: http://localhost:3000"
echo "👤 User: admin"
echo "🔑 Password: admin123"

echo ""
echo "📈 Expected Dashboards:"
echo "- DreamShops > Application Overview"
echo "- DreamShops > Business Metrics"

echo ""
echo "🚨 Expected Alerts:"
echo "- Application Down (Critical)"
echo "- High Error Rate (Warning)"
echo "- High Response Time (Warning)"
echo "- High JVM Memory Usage (Warning)"

echo ""
echo "✅ Grafana setup test completed!"
echo "👉 Access Grafana at: http://localhost:3000"