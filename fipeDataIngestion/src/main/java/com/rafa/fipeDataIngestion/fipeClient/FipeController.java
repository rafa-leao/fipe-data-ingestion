package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

@RestController
public class FipeController {
    private @Autowired BuscadorVeiculoService service;

    @GetMapping("/fipe/{tipoVeiculo}")
    public ResponseEntity<List<Veiculo>> buscaVeiculosFipePorTipo(@PathVariable String tipoVeiculo) {
        return ResponseEntity.ok(service.busca(tipoVeiculo));
    }

    @GetMapping("/fipe/marcas")
    public ResponseEntity<List<String>> buscaMarcasFipe() {
        return ResponseEntity.ok(service.buscaMarcas());
    }
}
