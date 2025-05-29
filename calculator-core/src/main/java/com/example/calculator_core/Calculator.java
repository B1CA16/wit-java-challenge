package com.example.calculator_core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class Calculator {

    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        logger.info("Performing sum operation: {} + {}", a, b);
        BigDecimal result = a.add(b);
        logger.info("Sum result: {}", result);
        return result;
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        logger.info("Performing subtract operation: {} - {}", a, b);
        BigDecimal result = a.subtract(b);
        logger.info("Subtract result: {}", result);
        return result;
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        logger.info("Performing multiply operation: {} * {}", a, b);
        BigDecimal result = a.multiply(b);
        logger.info("Multiply result: {}", result);
        return result;
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        logger.info("Performing divide operation: {} / {}", a, b);
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            logger.error("Division by zero attempted");
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        BigDecimal result = a.divide(b, 100, RoundingMode.HALF_UP);
        logger.info("Divide result: {}", result);
        return result;
    }

    public BigDecimal calculate(String operation, BigDecimal a, BigDecimal b) {
        String requestId = MDC.get("requestId");
        logger.info("Starting calculation for request: {} - Operation: {}", requestId, operation);

        try {
            return switch (operation.toLowerCase()) {
                case "sum", "add" -> sum(a, b);
                case "subtract", "sub" -> subtract(a, b);
                case "multiply", "mul" -> multiply(a, b);
                case "divide", "div" -> divide(a, b);
                default -> {
                    logger.error("Invalid operation: {}", operation);
                    throw new IllegalArgumentException("Invalid operation: " + operation);
                }
            };
        } catch (Exception e) {
            logger.error("Error in calculation for request: {} - {}", requestId, e.getMessage());
            throw e;
        }
    }
}