package com.rafa.fipeDataIngestion.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {

    @Query("SELECT DISTINCT v.name FROM VeiculoEntity v")
    List<String> findDistinctMarcas();

}
