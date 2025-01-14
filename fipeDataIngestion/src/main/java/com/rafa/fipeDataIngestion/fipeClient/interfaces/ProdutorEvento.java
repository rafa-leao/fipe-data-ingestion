package com.rafa.fipeDataIngestion.fipeClient.interfaces;

import java.util.List;

import com.rafa.fipeDataIngestion.fipeClient.model.Marca;

public interface ProdutorEvento {
    public void produzEvento(List<Marca> marcas, String tipoVeiculo);
}
