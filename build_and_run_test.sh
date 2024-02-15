#!/bin/bash

# Executar o comando gradle para limpar e construir a imagem do Docker
./gradlew shadowJar

# Construir a imagem
docker build -t rochajg/rinhav2:latest .

# Listar as imagens Docker disponíveis
docker image list rochajg/rinhav2:latest

# Remover os containers existentes e limpar a base de dados do MongoDB
docker compose down -v

# Iniciar os contêineres utilizando o docker-compose
docker compose up -d

# Executar o script de teste de carga
./load-test/run-test.sh
