#!/bin/bash

# Função para compilar e construir a imagem Docker
build_project() {
  local project_dir=$1
  local image_name=$2

  echo "Building project: $project_dir"
  cd $project_dir || exit
  ./mvnw clean install -DskipTests
  docker build -t $image_name:latest .
  cd ..
}

# Diretório raiz do projeto
ROOT_DIR=$(pwd)

# Compilar e construir as imagens Docker para cada projeto
build_project "$ROOT_DIR/dataPipelineAPI" "data-pipeline-api"
build_project "$ROOT_DIR/fipeDataIngestion" "fipe-data-ingestion"

# Reiniciar os serviços com Docker Compose
echo "Restarting services with Docker Compose"
docker-compose down
docker-compose up -d

echo "deployed successfully"
