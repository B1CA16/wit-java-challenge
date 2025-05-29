package com.example.calculator_core.model;

import java.math.BigDecimal;

public class CalculationResult {
    private String requestId;
    private BigDecimal result;
    private String error;
    private boolean success;

    public CalculationResult() {}

    public CalculationResult(String requestId, BigDecimal result) {
        this.requestId = requestId;
        this.result = result;
        this.success = true;
    }

    public CalculationResult(String requestId, String error) {
        this.requestId = requestId;
        this.error = error;
        this.success = false;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "requestId='" + requestId + '\'' +
                ", result=" + result +
                ", error='" + error + '\'' +
                ", success=" + success +
                '}';
    }
}