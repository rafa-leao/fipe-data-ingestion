package com.rafa.dataPipelineAPI.fipeClient;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.dataPipelineAPI.database.entity.MarcaEntity;
import com.rafa.dataPipelineAPI.database.entity.VeiculoEntity;
import com.rafa.dataPipelineAPI.database.repository.MarcaRepository;
import com.rafa.dataPipelineAPI.database.repository.VeiculoRepository;
import com.rafa.dataPipelineAPI.fipeClient.model.Marca;
import com.rafa.dataPipelineAPI.fipeClient.model.Veiculo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BuscadorMarcaService {
    private @Autowired WebClient webClient;
    private @Autowired MarcaRepository marcaRepository;
    private @Autowired VeiculoRepository veiculoRepository;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "fipe-marcas", partitions = {"0"}), containerFactory = "kafkaListenerContainerFactory")
    public void ouveEvento(Marca marca) {
        salvaMarca(marca)
                .flatMap(savedMarca -> buscaVeiculos(savedMarca)
                        .flatMapMany(veiculos -> salvaVeiculos(veiculos, savedMarca))
                        .then(Mono.just(savedMarca)))
                .subscribe(
                        savedVeiculo -> System.out.println("Veículo salvo: " + savedVeiculo),
                        error -> System.err.println("Erro ao salvar veículo: " + error.getMessage()));
    }

    private Mono<Marca> salvaMarca(Marca marca) {
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

    private Mono<List<Veiculo>> buscaVeiculos(Marca marca) {
        return webClient.get()
                .uri(marca.getTipo() + "/brands/" + marca.getCode() + "/models")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {
                });
    }

    private Flux<VeiculoEntity> salvaVeiculos(List<Veiculo> veiculos, Marca marca) {
        if (veiculos == null) {
            System.out.println("Veículos é nulo");
            return Flux.empty();
        }

        MarcaEntity marcaEntity = marcaRepository.findByCode(marca.getCode());

        List<VeiculoEntity> veiculoEntities = veiculos.stream()
                .filter(v -> v != null && v.getCode() != null && v.getName() != null)
                .map(v -> new VeiculoEntity(v.getCode(), v.getName(), v.getTipo(), v.getObservation(), marcaEntity))
                .collect(Collectors.toList());

        return Flux.fromIterable(veiculoEntities)
                .filter(veiculoEntity -> !veiculoRepository.existsByCode(veiculoEntity.getCode()))
                .flatMap(veiculoEntity -> Mono.fromCallable(() -> veiculoRepository.save(veiculoEntity)));
    }
}
