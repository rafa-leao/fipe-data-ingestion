package com.rafa.dataPipelineAPI.fipeClient.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rafa.dataPipelineAPI.database.entity.MarcaEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Marca {
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tipo")
    private String tipo;

    public static MarcaEntity toMarcaEntity(Marca entity) {
        return new MarcaEntity(entity.getCode(), entity.getName(), entity.getTipo());
    }

    public static List<MarcaEntity> toMarcaEntityList(List<Marca> entities) {
        return entities.stream()
                .map(Marca::toMarcaEntity)
                .collect(Collectors.toList());
    }
}
