package com.rafa.fipeDataIngestion.fipeClient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Veiculo {
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
}
