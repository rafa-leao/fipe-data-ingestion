package com.rafa.dataPipelineAPI.brokerConsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
}