package com.example.calculator_api.controller;

import com.example.calculator_api.model.CalculationResponse;
import com.example.calculator_api.service.CalculatorApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorApiService calculatorApiService;

    @GetMapping("/sum")
    public ResponseEntity<CalculationResponse> sum(
            @RequestParam BigDecimal a,
            @RequestParam BigDecimal b) {
        String requestId = generateRequestId();
        return performCalculation("sum", a, b, requestId);
    }

    @GetMapping("/subtract")
    public ResponseEntity<CalculationResponse> subtract(
            @RequestParam BigDecimal a,
            @RequestParam BigDecimal b) {
        String requestId = generateRequestId();
        return performCalculation("subtract", a, b, requestId);
    }

    @GetMapping("/multiply")
    public ResponseEntity<CalculationResponse> multiply(
            @RequestParam BigDecimal a,
            @RequestParam BigDecimal b) {
        String requestId = generateRequestId();
        return performCalculation("multiply", a, b, requestId);
    }

    @GetMapping("/divide")
    public ResponseEntity<CalculationResponse> divide(
            @RequestParam BigDecimal a,
            @RequestParam BigDecimal b) {
        String requestId = generateRequestId();
        return performCalculation("divide", a, b, requestId);
    }

    private ResponseEntity<CalculationResponse> performCalculation(
            String operation, BigDecimal a, BigDecimal b, String requestId) {

        // Set MDC for request tracking
        MDC.put("requestId", requestId);

        try {
            logger.info("Received {} request: a={}, b={}", operation, a, b);

            CompletableFuture<CalculationResponse> future =
                    calculatorApiService.performCalculation(operation, a, b, requestId);

            CalculationResponse response = future.get(); // This call blocks until the future completes

            logger.info("Calculation completed: {}", response);

            return ResponseEntity.ok()
                    .header("X-Request-ID", requestId)
                    .body(response);

        } catch (Exception e) {
            logger.error("Error processing calculation request: {}", e.getMessage());

            CalculationResponse errorResponse = new CalculationResponse();
            errorResponse.setError("Internal server error: " + e.getMessage());

            return ResponseEntity.internalServerError()
                    .header("X-Request-ID", requestId)
                    .body(errorResponse);
        } finally {
            MDC.clear();
        }
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}