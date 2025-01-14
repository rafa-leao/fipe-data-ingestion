package com.rafa.fipeDataIngestion.brokerProducer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.rafa.fipeDataIngestion.fipeClient.model.Marca;

@Configuration
public class BrokerProducer {

	private @Value(value = "${spring.broker.server}") String serverAddress;

	@Bean
	public ProducerFactory<String, Marca> producerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, Marca> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
}
