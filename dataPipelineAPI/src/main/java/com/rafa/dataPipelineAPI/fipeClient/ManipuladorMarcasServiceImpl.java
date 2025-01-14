package com.rafa.dataPipelineAPI.fipeClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import com.rafa.dataPipelineAPI.database.entity.MarcaEntity;
import com.rafa.dataPipelineAPI.database.repository.MarcaRepository;
import com.rafa.dataPipelineAPI.fipeClient.interfaces.EscutadorEvento;
import com.rafa.dataPipelineAPI.fipeClient.interfaces.ManipuladorMarcasFIPE;
import com.rafa.dataPipelineAPI.fipeClient.interfaces.ManipuladorVeiculosFIPE;
import com.rafa.dataPipelineAPI.fipeClient.model.Marca;

import reactor.core.publisher.Mono;

@Service
public class ManipuladorMarcasServiceImpl implements EscutadorEvento, ManipuladorMarcasFIPE {
    private @Autowired MarcaRepository marcaRepository;
    private @Autowired ManipuladorVeiculosFIPE veiculosService;

    @Override
    @KafkaListener(topicPartitions = @TopicPartition(topic = "fipe-marcas", partitions = {"0"}), containerFactory = "kafkaListenerContainerFactory")
    public void ouveEvento(Marca marca) {
        salvaMarca(marca)
                .flatMap(savedMarca -> veiculosService.buscaVeiculos(savedMarca)
                        .flatMapMany(veiculos -> veiculosService.salvaVeiculos(veiculos, savedMarca))
                        .then(Mono.just(savedMarca)))
                .subscribe(
                        savedVeiculo -> System.out.println("Veículo salvo: " + savedVeiculo),
                        error -> System.err.println("Erro ao salvar veículo: " + error.getMessage()));
    }

    @Override
    public Mono<Marca> salvaMarca(Marca marca) {
        if (marca == null || marca.getTipo() == null) {
            System.out.println("Marca ou tipoVeiculo é nulo");
            return Mono.empty();
        }

        MarcaEntity marcaEntity = new MarcaEntity(marca.getCode(), marca.getName(), marca.getTipo());

        return Mono.fromCallable(() -> {
            if (!marcaRepository.existsByCode(marcaEntity.getCode())) {
                return marcaRepository.save(marcaEntity);
            } else {
                return marcaRepository.findByCode(marcaEntity.getCode());
            }
        }).map(savedEntity -> new Marca(savedEntity.getCode(), savedEntity.getName(), savedEntity.getTipo()));
    }
}
