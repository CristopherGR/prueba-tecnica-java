package com.exercise.client_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name("clientCreatedTopic")
                .replicas(1)
                .partitions(3)
                .build();
    }
}
