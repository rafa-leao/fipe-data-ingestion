package com.rafa.dataPipelineAPI.fipeClient;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.dataPipelineAPI.brokerConsumer.Marca;
import com.rafa.dataPipelineAPI.database.MarcaEntity;
import com.rafa.dataPipelineAPI.database.MarcaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BuscadorMarcaService {
    private @Autowired WebClient webClient;
    private @Autowired MarcaRepository marcaRepository;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "fipe-marcas", partitions = {"0"}), containerFactory = "kafkaListenerContainerFactory")
    public void ouveEvento(Marca marca) {
        this.busca(marca.getTipo(), marca.getCode())
            .flatMapMany(marcas -> salvaMarcas(marcas, marca.getTipo()))
            .subscribe(
                savedMarca -> System.out.println("Marca salva: " + savedMarca),
                error -> System.err.println("Erro ao salvar marca: " + error.getMessage())
            );
    }

    private Mono<List<Marca>> busca(String tipoVeiculo, String codeMarca) {
        return webClient.get()
                .uri(tipoVeiculo + "/brands/" + codeMarca + "/models")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Marca>>() {
                });
    }

    private Flux<MarcaEntity> salvaMarcas(List<Marca> marcas, String tipoVeiculo) {
        if (marcas == null || tipoVeiculo == null) {
            System.out.println("Marcas ou tipoVeiculo é nulo");
            return Flux.empty();
        }

        List<MarcaEntity> marcaEntities = marcas.stream()
                .filter(v -> v != null && v.getCode() != null && v.getName() != null)
                .map(v -> new MarcaEntity(v.getCode(), v.getName(), tipoVeiculo))
                .collect(Collectors.toList());

        return Flux.fromIterable(marcaEntities)
                .filter(marcaEntity -> !marcaRepository.existsByCode(marcaEntity.getCode()))
                .flatMap(marcaEntity -> Mono.fromCallable(() -> marcaRepository.save(marcaEntity)));
    }
}
