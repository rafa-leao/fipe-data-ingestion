package com.rafa.dataPipelineAPI.fipeClient.interfaces;

import java.util.List;

import com.rafa.dataPipelineAPI.database.entity.VeiculoEntity;
import com.rafa.dataPipelineAPI.fipeClient.model.Marca;
import com.rafa.dataPipelineAPI.fipeClient.model.Veiculo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManipuladorVeiculosFIPE {
    public Mono<List<Veiculo>> buscaVeiculos(Marca marca);

    public Flux<VeiculoEntity> salvaVeiculos(List<Veiculo> veiculos, Marca marca);
}
