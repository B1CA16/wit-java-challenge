package com.example.calculator_api;

import com.example.calculator_api.controller.CalculatorController;
import com.example.calculator_api.model.CalculationResponse;
import com.example.calculator_api.service.CalculatorApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculatorApiService calculatorApiService;

    @Test
    public void testSum() throws Exception {
        CalculationResponse response = new CalculationResponse(new BigDecimal("5"));
        CompletableFuture<CalculationResponse> future = CompletableFuture.completedFuture(response);

        when(calculatorApiService.performCalculation(eq("sum"), any(BigDecimal.class), any(BigDecimal.class), anyString()))
                .thenReturn(future);

        mockMvc.perform(get("/api/calculator/sum")
                        .param("a", "2")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5))
                .andExpect(header().exists("X-Request-ID"));
    }

    @Test
    public void testSubtract() throws Exception {
        CalculationResponse response = new CalculationResponse(new BigDecimal("2"));
        CompletableFuture<CalculationResponse> future = CompletableFuture.completedFuture(response);

        when(calculatorApiService.performCalculation(eq("subtract"), any(BigDecimal.class), any(BigDecimal.class), anyString()))
                .thenReturn(future);

        mockMvc.perform(get("/api/calculator/subtract")
                        .param("a", "5")
                        .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2))
                .andExpect(header().exists("X-Request-ID"));
    }

    @Test
    public void testMultiply() throws Exception {
        CalculationResponse response = new CalculationResponse(new BigDecimal("15"));
        CompletableFuture<CalculationResponse> future = CompletableFuture.completedFuture(response);

        when(calculatorApiService.performCalculation(eq("multiply"), any(BigDecimal.class), any(BigDecimal.class), anyString()))
                .thenReturn(future);

        mockMvc.perform(get("/api/calculator/multiply")
                        .param("a", "3")
                        .param("b", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(15))
                .andExpect(header().exists("X-Request-ID"));
    }

    @Test
    public void testDivide() throws Exception {
        CalculationResponse response = new CalculationResponse(new BigDecimal("2.5"));
        CompletableFuture<CalculationResponse> future = CompletableFuture.completedFuture(response);

        when(calculatorApiService.performCalculation(eq("divide"), any(BigDecimal.class), any(BigDecimal.class), anyString()))
                .thenReturn(future);

        mockMvc.perform(get("/api/calculator/divide")
                        .param("a", "10")
                        .param("b", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.5))
                .andExpect(header().exists("X-Request-ID"));
    }
}