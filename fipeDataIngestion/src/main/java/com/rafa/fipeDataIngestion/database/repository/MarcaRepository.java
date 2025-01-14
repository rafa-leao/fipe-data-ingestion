package com.rafa.fipeDataIngestion.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.fipeDataIngestion.database.entity.MarcaEntity;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {
    boolean existsByCode(String code);
    // @Query("SELECT DISTINCT v.name FROM VeiculoEntity v")
    // List<String> findDistinctMarcas();

    // List<Marca> findByCode(String code);

}
