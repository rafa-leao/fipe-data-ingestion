package com.rafa.fipeDataIngestion.fipeClient.interfaces;

import java.util.List;

import com.rafa.fipeDataIngestion.fipeClient.model.Marca;

public interface ManipuladorMarcasFIPE {
    public void iniciaMarcas();

    public List<Marca> buscaMarcas();
}
