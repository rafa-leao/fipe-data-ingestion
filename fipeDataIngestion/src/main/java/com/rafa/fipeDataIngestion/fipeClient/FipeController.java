package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.fipeDataIngestion.fipeClient.interfaces.ManipuladorMarcasFIPE;
import com.rafa.fipeDataIngestion.fipeClient.interfaces.ManipuladorVeiculosFIPE;
import com.rafa.fipeDataIngestion.fipeClient.model.Marca;
import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

@RestController
@RequestMapping("/fipe")
public class FipeController {
    private @Autowired ManipuladorMarcasFIPE buscadorMarca;
    private @Autowired ManipuladorVeiculosFIPE buscadorVeiculo;

    @PostMapping("/marcas")
    public ResponseEntity<String> geraMarcas() {
        buscadorMarca.iniciaMarcas();
        return ResponseEntity.ok("Marcas geradas com sucesso");
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> buscaMarcas() {
        return ResponseEntity.ok(buscadorMarca.buscaMarcas());
    }

    @GetMapping("/veiculos/{marcaId}")
    public ResponseEntity<List<Veiculo>> buscaVeiculoPorMarca(@PathVariable Long marcaId) {
        return ResponseEntity.ok(buscadorVeiculo.buscaVeiculosSalvos(marcaId));
    }

    @PutMapping("/veiculos/{veiculoId}")
    public ResponseEntity<String> atualizaVeiculo(@PathVariable String veiculoId, @RequestBody Veiculo veiculo) {    
        Optional<Veiculo> veiculoSalvo = buscadorVeiculo.buscaVeiculoPorId(veiculoId, veiculo);
        if (!veiculoSalvo.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok("Veículo atualizado com sucesso");
    }
}
