package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

@Service
public class BuscadorVeiculoService {
    private @Autowired WebClient webClient;

    private @Autowired KafkaTemplate<String, Veiculo> broker;

    public List<Veiculo> busca(String veiculo) {
        List<Veiculo> veiculos = webClient.get()
            .uri(veiculo + "/brands")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {})
            .block();
        this.produzEvento(veiculos);
        return veiculos;
    }

    private void produzEvento(List<Veiculo> veiculos) {
        veiculos.forEach(veiculo -> {
            broker.send("fipe-marcas", veiculo);
        });
    }
}
