package com.example.calculator_api.service;

import com.example.calculator_api.kafka.CalculationProducer;
import com.example.calculator_api.model.CalculationRequest;
import com.example.calculator_api.model.CalculationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class CalculatorApiService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorApiService.class);

    @Autowired
    private CalculationProducer calculationProducer;

    // Store pending requests
    private final ConcurrentHashMap<String, CompletableFuture<CalculationResponse>> pendingRequests =
            new ConcurrentHashMap<>();

    public CompletableFuture<CalculationResponse> performCalculation(
            String operation, BigDecimal a, BigDecimal b, String requestId) {

        MDC.put("requestId", requestId);

        logger.info("Starting calculation request: {} - {} {} {}", requestId, a, operation, b);

        CompletableFuture<CalculationResponse> future = new CompletableFuture<>();

        // Set timeout for the future
        future.orTimeout(30, TimeUnit.SECONDS);

        // Store the future for later completion
        pendingRequests.put(requestId, future);

        try {
            // Create and send request via Kafka
            CalculationRequest request = new CalculationRequest(requestId, operation, a, b);
            calculationProducer.sendRequest(request);

            logger.info("Calculation request sent to Kafka: {}", request);

        } catch (Exception e) {
            logger.error("Error sending calculation request: {}", e.getMessage());
            future.completeExceptionally(e);
            pendingRequests.remove(requestId);
        }

        return future;
    }

    public void completeCalculation(String requestId, CalculationResponse response) {
        MDC.put("requestId", requestId);

        logger.info("Completing calculation for request: {} - {}", requestId, response);

        CompletableFuture<CalculationResponse> future = pendingRequests.remove(requestId);

        if (future != null) {
            future.complete(response);
            logger.info("Calculation completed successfully for request: {}", requestId);
        } else {
            logger.warn("No pending request found for ID: {}", requestId);
        }

        MDC.clear();
    }
}