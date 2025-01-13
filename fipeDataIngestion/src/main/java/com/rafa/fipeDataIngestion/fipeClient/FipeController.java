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

import com.rafa.fipeDataIngestion.database.entity.MarcaEntity;
import com.rafa.fipeDataIngestion.database.entity.VeiculoEntity;
import com.rafa.fipeDataIngestion.database.repository.MarcaRepository;
import com.rafa.fipeDataIngestion.database.repository.VeiculoRepository;
import com.rafa.fipeDataIngestion.fipeClient.model.Marca;
import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

@RestController
@RequestMapping("/fipe")
public class FipeController {
    private @Autowired BuscadorMarcaService buscadorMarca;
    private @Autowired MarcaRepository marcaRepository;
    private @Autowired VeiculoRepository veiculoRepository;

    @PostMapping("/marcas")
    public ResponseEntity<String> geraMarcas() {
        buscadorMarca.gera();
        return ResponseEntity.ok("Marcas geradas com sucesso");
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> buscaMarcas() {
        List<MarcaEntity> marcaEntities = marcaRepository.findAll();
        List<Marca> marcas = MarcaEntity.toMarcaList(marcaEntities);
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/veiculos/{marcaId}")
    public ResponseEntity<List<Veiculo>> buscaVeiculoPorMarca(@PathVariable Long marcaId) {
        List<VeiculoEntity> veiculoEntities = veiculoRepository.findByMarcaId(marcaId);
        List<Veiculo> veiculos = VeiculoEntity.toVeiculoList(veiculoEntities);
        return ResponseEntity.ok(veiculos);
    }

    @PutMapping("/veiculos/{veiculoId}")
    public ResponseEntity<String> atualizaVeiculo(@PathVariable String veiculoId, @RequestBody Veiculo veiculo) {
        Optional<VeiculoEntity> veiculoEntityOptional = veiculoRepository.findByCode(veiculoId);
        
        if (!veiculoEntityOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        VeiculoEntity veiculoEntity = veiculoEntityOptional.get();
        veiculoEntity.setName(veiculo.getName());
        veiculoEntity.setObservation(veiculo.getObservation());
        veiculoRepository.save(veiculoEntity);
        return ResponseEntity.ok("Veículo atualizado com sucesso");
    }
}
