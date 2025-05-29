package com.example.calculator_api.model;

import java.math.BigDecimal;

public class CalculationRequest {
    private String requestId;
    private String operation;
    private BigDecimal operandA;
    private BigDecimal operandB;

    public CalculationRequest() {}

    public CalculationRequest(String requestId, String operation, BigDecimal operandA, BigDecimal operandB) {
        this.requestId = requestId;
        this.operation = operation;
        this.operandA = operandA;
        this.operandB = operandB;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getOperandA() {
        return operandA;
    }

    public void setOperandA(BigDecimal operandA) {
        this.operandA = operandA;
    }

    public BigDecimal getOperandB() {
        return operandB;
    }

    public void setOperandB(BigDecimal operandB) {
        this.operandB = operandB;
    }

    @Override
    public String toString() {
        return "CalculationRequest{" +
                "requestId='" + requestId + '\'' +
                ", operation='" + operation + '\'' +
                ", operandA=" + operandA +
                ", operandB=" + operandB +
                '}';
    }
}