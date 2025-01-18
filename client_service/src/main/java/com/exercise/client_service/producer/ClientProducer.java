package com.exercise.client_service.producer;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.service.utils.JsonUtils;
import com.exercise.client_service.producer.common.ClientProducerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClientProducer {
    private static final Logger log = LoggerFactory.getLogger(ClientProducer.class);

    String TOPIC = "clientCreatedTopic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ClientProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCreatedClient(Client client) {
        log.info("Sending message to Kafka Topic -> {}", TOPIC);

        ClientProducerDTO clientProducerDTO = new ClientProducerDTO(client.getClientId());
        kafkaTemplate.send(TOPIC, JsonUtils.toJson(clientProducerDTO));

        log.info("Message Sent -> {}", clientProducerDTO);
    }
}
