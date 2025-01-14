package com.rafa.dataPipelineAPI.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafa.dataPipelineAPI.database.entity.VeiculoEntity;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {
    boolean existsByCode(String code);
}