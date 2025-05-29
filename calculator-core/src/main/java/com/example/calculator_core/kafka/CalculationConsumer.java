package com.example.calculator_core.kafka;

import com.example.calculator_core.service.CalculatorService;
import com.example.calculator_core.model.CalculationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CalculationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CalculationConsumer.class);

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "calculation-requests", groupId = "calculator-core-group")
    public void consume(String message) {
        logger.info("Received calculation request: {}", message);

        try {
            CalculationRequest request = objectMapper.readValue(message, CalculationRequest.class);
            calculatorService.processCalculation(request);
        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}", e.getMessage());
        }
    }
}