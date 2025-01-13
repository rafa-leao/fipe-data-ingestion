package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.fipeDataIngestion.database.entity.MarcaEntity;
import com.rafa.fipeDataIngestion.database.repository.MarcaRepository;
import com.rafa.fipeDataIngestion.fipeClient.model.Marca;

@RestController
@RequestMapping("/fipe")
public class FipeController {
    private @Autowired BuscadorMarcaService buscadorMarca;
    private @Autowired MarcaRepository marcaRepository;

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
}
