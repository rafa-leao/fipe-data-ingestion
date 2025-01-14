package com.rafa.dataPipelineAPI.fipeClient.interfaces;

import com.rafa.dataPipelineAPI.fipeClient.model.Marca;

import reactor.core.publisher.Mono;

public interface ManipuladorMarcasFIPE {
    public Mono<Marca> salvaMarca(Marca marca);
}
