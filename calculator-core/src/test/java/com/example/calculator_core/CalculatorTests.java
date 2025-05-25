package com.example.calculator_core;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTests {

	private Calculator calculator;

	@BeforeEach
	public void setup() {
		calculator = new Calculator();
	}

	@Test
	public void testSum() {
		BigDecimal result = calculator.sum(new BigDecimal("1.5"), new BigDecimal("2.3"));
		assertEquals(new BigDecimal("3.8"), result);
	}

	@Test
	public void testSubtract() {
		BigDecimal result = calculator.subtract(new BigDecimal("5.0"), new BigDecimal("3.2"));
		assertEquals(new BigDecimal("1.8"), result);
	}

	@Test
	public void testMultiply() {
		BigDecimal result = calculator.multiply(new BigDecimal("2"), new BigDecimal("3.5"));
		assertEquals(new BigDecimal("7.0"), result);
	}

	@Test
	public void testDivide() {
		BigDecimal result = calculator.divide(new BigDecimal("10"), new BigDecimal("4"));
		assertEquals(new BigDecimal("2.5"), result.stripTrailingZeros());
	}

	@Test
	public void testDivideByZero() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			calculator.divide(new BigDecimal("5"), BigDecimal.ZERO);
		});
		assertEquals("Division by zero is not allowed.", exception.getMessage());
	}
}
