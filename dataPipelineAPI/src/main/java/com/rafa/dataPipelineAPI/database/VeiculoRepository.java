package com.rafa.dataPipelineAPI.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {

    boolean existsByCode(String code);

}
