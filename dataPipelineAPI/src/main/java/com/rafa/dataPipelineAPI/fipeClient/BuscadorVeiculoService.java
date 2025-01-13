package com.rafa.dataPipelineAPI.fipeClient;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.dataPipelineAPI.database.VeiculoEntity;
import com.rafa.dataPipelineAPI.database.VeiculoRepository;
import com.rafa.dataPipelineAPI.model.Veiculo;

@Service
public class BuscadorVeiculoService {
    private @Autowired WebClient webClient;
    private @Autowired VeiculoRepository veiculoRepository;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "fipe-marcas", partitions = {
            "0" }), containerFactory = "kafkaListenerContainerFactory")
    public void buscaEvento(Veiculo veiculo) {
        this.busca(veiculo.getTipo(), veiculo.getCode());
    }

    private void busca(String tipoVeiculo, String IDMarca) {
        List<Veiculo> veiculos = webClient.get()
                // .uri(tipoVeiculo + "/brands/" + IDMarca + "/models")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {
                })
                .block();
        this.salvaVeiculo(veiculos, tipoVeiculo);
    }

    private void salvaVeiculo(List<Veiculo> veiculos, String tipoVeiculo) {
        if (veiculos == null || tipoVeiculo == null) {
            return;
        }

        List<VeiculoEntity> veiculoEntities = veiculos.stream()
                .filter(v -> v != null && v.getCode() != null && v.getName() != null)
                .map(v -> new VeiculoEntity(v.getCode(), v.getName(), tipoVeiculo))
                .collect(Collectors.toList());

        for (VeiculoEntity veiculoEntity : veiculoEntities) {
            if (!veiculoRepository.existsByCode(veiculoEntity.getCode())) {
                veiculoRepository.save(veiculoEntity);
            } else {
                System.out.println("VEICULO JÁ EXISTE NO BANCO DE DADOS: " + veiculoEntity);
            }
        }
    }
}
