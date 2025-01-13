#!/bin/bash

# Parar e remover os serviços com Docker Compose
echo "Stopping and removing services with Docker Compose"
./undeploy.sh

# Compilar, construir as imagens Docker e reiniciar os serviços com Docker Compose
echo "Rebuilding and restarting services with Docker Compose"
./deploy.sh

echo "reloaded successfully"
