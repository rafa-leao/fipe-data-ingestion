package com.rafa.fipeDataIngestion.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.fipeDataIngestion.fipeClient.model.Veiculo;

@Service
public class TipoVeiculoService {
    private @Autowired WebClient webClient;

    public List<Veiculo> busca(String veiculo) {
        return webClient.get()
            .uri(veiculo + "/brands")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {})
            .block();    
    }
}
