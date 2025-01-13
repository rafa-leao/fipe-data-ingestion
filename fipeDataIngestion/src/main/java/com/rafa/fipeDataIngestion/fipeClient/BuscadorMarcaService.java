package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.fipeDataIngestion.fipeClient.model.Marca;

@Service
public class BuscadorMarcaService {
    private @Autowired WebClient webClient;
    private @Autowired KafkaTemplate<String, Marca> broker;

    public void gera() {
        List<String> tipos = List.of("cars", "motorcycles", "trucks");

        tipos.forEach(tipoVeiculo -> {
            webClient.get()
                    .uri(tipoVeiculo + "/brands")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Marca>>() {
                    })
                    .subscribe(marcas -> {
                        this.produzEvento(marcas, tipoVeiculo);
                    }, error -> {
                        System.err
                                .println("Erro ao carregar marcas do tipo: " + tipoVeiculo + ": " + error.getMessage());
                    });
        });
    }

    private void produzEvento(List<Marca> marcas, String tipoVeiculo) {
        marcas.forEach(marca -> {
            marca.setTipo(tipoVeiculo);
            broker.send("fipe-marcas", marca);
        });
    }
}
