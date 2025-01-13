package com.rafa.fipeDataIngestion.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "veiculos")
public class VeiculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String observation;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private MarcaEntity marca;

    public VeiculoEntity(String code, String name, String observation, MarcaEntity marca) {
        this.code = code;
        this.name = name;
        this.observation = observation;
        this.marca = marca;
    }
}
