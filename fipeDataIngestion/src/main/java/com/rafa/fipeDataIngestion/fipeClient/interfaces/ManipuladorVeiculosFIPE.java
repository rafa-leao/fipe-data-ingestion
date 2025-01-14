package com.rafa.fipeDataIngestion.fipeClient.interfaces;

import java.util.List;
import java.util.Optional;

import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

public interface ManipuladorVeiculosFIPE {
    public List<Veiculo> buscaVeiculosSalvos(Long marcaId);

    public Optional<Veiculo> atualizaVeiculoPorId(String veiculoId, Veiculo veiculo);
}
