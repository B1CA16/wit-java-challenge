package com.example.calculator_api.kafka;

import com.example.calculator_api.model.CalculationResponse;
import com.example.calculator_api.service.CalculatorApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CalculationConsumer.class);

    @Autowired
    private CalculatorApiService calculatorApiService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "calculation-results", groupId = "calculator-api-group")
    public void consume(String message) {
        logger.info("Received calculation result: {}", message);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String requestId = jsonNode.get("requestId").asText();
            boolean success = jsonNode.get("success").asBoolean();

            CalculationResponse response = new CalculationResponse();

            if (success) {
                BigDecimal result = new BigDecimal(jsonNode.get("result").asText());
                response.setResult(result);
            } else {
                String error = jsonNode.get("error").asText();
                response.setError(error);
            }

            calculatorApiService.completeCalculation(requestId, response);

        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}", e.getMessage());
        }
    }
}