package com.example.calculator_core;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a.divide(b, 100, RoundingMode.HALF_UP);
    }
}
