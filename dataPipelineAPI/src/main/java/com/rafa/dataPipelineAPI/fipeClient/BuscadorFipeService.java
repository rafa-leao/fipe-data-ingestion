package com.rafa.dataPipelineAPI.fipeClient;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import com.rafa.dataPipelineAPI.brokerConsumer.model.Veiculo;

@Service
public class BuscadorFipeService {
    @KafkaListener(topicPartitions = @TopicPartition(topic = "fipe-marcas", partitions = { "0" }), containerFactory = "kafkaListenerContainerFactory")
    public void busca(Veiculo veiculo) {
        System.out.println(veiculo);
    }    
}
