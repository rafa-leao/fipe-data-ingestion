package com.rafa.fipeDataIngestion.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafa.fipeDataIngestion.database.entity.VeiculoEntity;

@Repository
public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {
    
    boolean existsByCode(String code);

    List<VeiculoEntity> findByMarcaCode(String marcaCode);

    Optional<VeiculoEntity> findByCode(String code);
}