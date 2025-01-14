package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.fipeDataIngestion.database.entity.MarcaEntity;
import com.rafa.fipeDataIngestion.database.repository.MarcaRepository;
import com.rafa.fipeDataIngestion.fipeClient.interfaces.ManipuladorMarcasFIPE;
import com.rafa.fipeDataIngestion.fipeClient.interfaces.ProdutorEvento;
import com.rafa.fipeDataIngestion.fipeClient.model.Marca;

@Service
public class ManipuladorMarcaServiceImpl implements ManipuladorMarcasFIPE, ProdutorEvento {
    private @Autowired WebClient webClient;
    private @Autowired MarcaRepository marcaRepository;
    private @Autowired KafkaTemplate<String, Marca> broker;

    @Override
    public void iniciaMarcas() {
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

    @Override
    public List<Marca> buscaMarcas() {
        List<MarcaEntity> marcaEntities = marcaRepository.findAll();
        return MarcaEntity.toMarcaList(marcaEntities);
    }

    @Override
    public void produzEvento(List<Marca> marcas, String tipoVeiculo) {
        marcas.forEach(marca -> {
            marca.setTipo(tipoVeiculo);
            broker.send("fipe-marcas", marca);
        });
    }
}
