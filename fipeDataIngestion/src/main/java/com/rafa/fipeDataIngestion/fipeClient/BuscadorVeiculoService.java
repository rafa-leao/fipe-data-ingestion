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

    public List<Veiculo> busca(String tipoVeiculo) {
        List<Veiculo> veiculos = webClient.get()
                // .uri(tipoVeiculo + "/brands")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {
                })
                .block();
        this.produzEvento(veiculos, tipoVeiculo);
        return veiculos;
    }

    private void produzEvento(List<Veiculo> veiculos, String tipoVeiculo) {
        veiculos.forEach(veiculo -> {
            veiculo.setTipo(tipoVeiculo);
            broker.send("fipe-marcas", veiculo);
        });
    }
}
