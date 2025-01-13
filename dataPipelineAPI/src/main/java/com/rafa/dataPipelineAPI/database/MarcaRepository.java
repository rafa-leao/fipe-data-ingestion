package com.rafa.dataPipelineAPI.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {

    boolean existsByCode(String code);

}
