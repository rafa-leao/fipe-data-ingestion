package com.rafa.dataPipelineAPI.fipeClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafa.dataPipelineAPI.model.Veiculo;

@Service
public class BuscadorFipeService {
	private @Autowired WebClient webClient;

	@KafkaListener(topicPartitions = @TopicPartition(topic = "fipe-marcas", partitions = {
			"0" }), containerFactory = "kafkaListenerContainerFactory")
	public void buscaEvento(Veiculo veiculo) {
		this.busca(veiculo.getTipo(), veiculo.getCode());
	}

	private void busca(String tipoVeiculo, String IDMarca) {
		webClient.get()
				.uri(tipoVeiculo + "/brands/" + IDMarca + "/models")
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Veiculo>>() {
				})
				.block();
	}
}
