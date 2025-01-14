# FIPE Data Ingestion

Este projeto consiste em duas APIs Spring Boot que trabalham juntas para ingerir dados do serviço FIPE e armazená-los em um banco de dados PostgreSQL. A comunicação entre as APIs é feita através do Apache Kafka.

## Estrutura do Projeto

- `fipeDataIngestion`: Inicia carga inicial de marcas e os envia para o Broker Kafka.
- `dataPipelineAPI`: Consome eventos Kafka e busca dados de veículos no serviço FIPE.

## Pré-requisitos

- Docker
- Docker Compose

## Deploy Local

### Usando os Scripts Shell em ambientes Unix-Like

- Se estiver em um ambiente Windows, considere utilizar [WSL(Windows Subsystem for Linux)](https://learn.microsoft.com/pt-br/windows/wsl/)

- Para facilitar o deploy dos servidores, foram criados três scripts shell: `deploy.sh`, `undeploy.sh` e `reload.sh`.

- Execute-os após clonar o projeto 

## Endpoints

- `POST /fipe/marcas`: Inicia a carga inicial das marcas.
- `GET /fipe/marcas`: Busca todas as marcas armazenadas.
- `GET /fipe/veiculos/{marcaCode}`: Busca todos os veículos de uma marca específica.
- `PUT /fipe/veiculos/{veiculoId}`: Atualiza os dados de um veículo específico.

### Requests curl


- `POST /fipe/marcas`
    - `curl -X POST http://localhost:8082/fipe/marcas`

- `GET /fipe/marcas`
    - `curl http://localhost:8082/fipe/marcas`

- `GET /fipe/veiculos/{marcaCode}`
    - `curl http://localhost:8082/fipe/veiculos/{marcaCode}`

- `PUT /fipe/veiculos/{veiculoId}`
    ```sh
    curl -X PUT http://localhost:8082/fipe/veiculos/{veiculoId} \
        -H "Content-Type: application/json" \
        -d '{"name": "Novo Modelo", "observation": "Nova Observação"}'
