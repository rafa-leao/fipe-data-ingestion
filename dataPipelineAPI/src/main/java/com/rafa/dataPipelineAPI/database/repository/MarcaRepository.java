package com.rafa.dataPipelineAPI.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.dataPipelineAPI.database.entity.MarcaEntity;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {

    boolean existsByCode(String code);

    MarcaEntity findByCode(String code);
}
