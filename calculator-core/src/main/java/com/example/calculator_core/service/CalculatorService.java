package com.example.calculator_core.service;

import com.example.calculator_core.Calculator;
import com.example.calculator_core.kafka.CalculationProducer;
import com.example.calculator_core.model.CalculationRequest;
import com.example.calculator_core.model.CalculationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    @Autowired
    private Calculator calculator;

    @Autowired
    private CalculationProducer calculationProducer;

    public void processCalculation(CalculationRequest request) {
        // Set MDC for request tracking
        MDC.put("requestId", request.getRequestId());

        logger.info("Processing calculation request: {}", request);

        try {
            BigDecimal result = calculator.calculate(
                    request.getOperation(),
                    request.getOperandA(),
                    request.getOperandB()
            );

            CalculationResult calculationResult = new CalculationResult(request.getRequestId(), result);
            logger.info("Calculation completed successfully: {}", calculationResult);

            // Send result back via Kafka
            calculationProducer.sendResult(calculationResult);

        } catch (Exception e) {
            logger.error("Error processing calculation: {}", e.getMessage());

            CalculationResult errorResult = new CalculationResult(request.getRequestId(), e.getMessage());
            calculationProducer.sendResult(errorResult);
        } finally {
            // Clear MDC
            MDC.clear();
        }
    }
}