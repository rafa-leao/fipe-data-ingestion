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
    private @Autowired TipoVeiculoService tipoVeiculo;

    @GetMapping("/fipe/{veiculo}")
    public ResponseEntity<List<Veiculo>> buscaMarcasFipe(@PathVariable String veiculo) {
        return ResponseEntity.ok(tipoVeiculo.busca(veiculo));
    }
}
