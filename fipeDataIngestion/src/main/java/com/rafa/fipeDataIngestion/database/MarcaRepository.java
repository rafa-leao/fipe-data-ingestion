package com.rafa.fipeDataIngestion.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {

    // @Query("SELECT DISTINCT v.name FROM VeiculoEntity v")
    // List<String> findDistinctMarcas();

    // List<Marca> findByCode(String code);

}
