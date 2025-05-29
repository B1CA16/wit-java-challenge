package com.example.calculator_core.kafka;

import com.example.calculator_core.model.CalculationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculationProducer {

    private static final Logger logger = LoggerFactory.getLogger(CalculationProducer.class);
    private static final String TOPIC = "calculation-results";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendResult(CalculationResult result) {
        try {
            String message = objectMapper.writeValueAsString(result);
            logger.info("Sending calculation result: {}", message);

            kafkaTemplate.send(TOPIC, result.getRequestId(), message);
            logger.info("Calculation result sent successfully");

        } catch (Exception e) {
            logger.error("Error sending calculation result: {}", e.getMessage());
        }
    }
}