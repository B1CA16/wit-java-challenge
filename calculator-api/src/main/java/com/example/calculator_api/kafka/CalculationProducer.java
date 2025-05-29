package com.example.calculator_api.kafka;

import com.example.calculator_api.model.CalculationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculationProducer {

    private static final Logger logger = LoggerFactory.getLogger(CalculationProducer.class);
    private static final String TOPIC = "calculation-requests";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendRequest(CalculationRequest request) {
        try {
            String message = objectMapper.writeValueAsString(request);
            logger.info("Sending calculation request: {}", message);

            kafkaTemplate.send(TOPIC, request.getRequestId(), message);
            logger.info("Calculation request sent successfully");

        } catch (Exception e) {
            logger.error("Error sending calculation request: {}", e.getMessage());
            throw new RuntimeException("Failed to send calculation request", e);
        }
    }
}