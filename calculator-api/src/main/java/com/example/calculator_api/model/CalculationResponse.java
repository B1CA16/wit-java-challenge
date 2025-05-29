package com.example.calculator_api.model;

import java.math.BigDecimal;

public class CalculationResponse {
    private BigDecimal result;
    private String error;

    public CalculationResponse() {}

    public CalculationResponse(BigDecimal result) {
        this.result = result;
    }

    public CalculationResponse(String error) {
        this.error = error;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CalculationResponse{" +
                "result=" + result +
                ", error='" + error + '\'' +
                '}';
    }
}