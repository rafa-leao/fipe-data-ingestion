package com.rafa.dataPipelineAPI.fipeClient;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.dataPipelineAPI.database.entity.MarcaEntity;
import com.rafa.dataPipelineAPI.database.entity.VeiculoEntity;
import com.rafa.dataPipelineAPI.database.repository.MarcaRepository;
import com.rafa.dataPipelineAPI.database.repository.VeiculoRepository;
import com.rafa.dataPipelineAPI.fipeClient.interfaces.ManipuladorVeiculosFIPE;
import com.rafa.dataPipelineAPI.fipeClient.model.Marca;
import com.rafa.dataPipelineAPI.fipeClient.model.Veiculo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ManipuladorVeiculosServiceImpl implements ManipuladorVeiculosFIPE{
    private @Autowired WebClient webClient;
    private @Autowired MarcaRepository marcaRepository;
    private @Autowired VeiculoRepository veiculoRepository;

    public Mono<List<Veiculo>> buscaVeiculos(Marca marca) {
        return webClient.get()
                .uri(marca.getTipo() + "/brands/" + marca.getCode() + "/models")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {
                });
    }

    public Flux<VeiculoEntity> salvaVeiculos(List<Veiculo> veiculos, Marca marca) {
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
