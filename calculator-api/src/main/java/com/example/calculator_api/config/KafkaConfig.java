package com.example.calculator_api.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic calculationRequestsTopic() {
        return TopicBuilder.name("calculation-requests")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic calculationResultsTopic() {
        return TopicBuilder.name("calculation-results")
                .partitions(3)
                .replicas(1)
                .build();
    }
}