package com.rafa.dataPipelineAPI.database.entity;

import java.util.List;
import java.util.stream.Collectors;

import com.rafa.dataPipelineAPI.brokerConsumer.Marca;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "marcas")
public class MarcaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String tipo;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VeiculoEntity> veiculos;

    public MarcaEntity(String code, String name, String tipo) {
        this.code = code;
        this.name = name;
        this.tipo = tipo;
    }

    public static Marca toMarca(MarcaEntity entity) {
        return new Marca(entity.getCode(), entity.getName(), entity.getTipo());
    }

    public static List<Marca> toMarcaList(List<MarcaEntity> entities) {
        return entities.stream()
                .map(MarcaEntity::toMarca)
                .collect(Collectors.toList());
    }
}
