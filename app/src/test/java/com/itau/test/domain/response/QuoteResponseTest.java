package com.itau.test.domain.response;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuoteResponseTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidQuoteResponse() {
        CustomerResponse customer = CustomerResponse.builder()
                .document_number("123456789")
                .name("John Doe")
                .type("individual")
                .gender("male")
                .date_of_birth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .phone_number(1234567890L)
                .build();

        QuoteResponse quoteResponse = QuoteResponse.builder()
                .id("quote123")
                .product_id("prod123")
                .offer_id("offer123")
                .category("category1")
                .total_monthly_premium_amount(BigDecimal.valueOf(100.00))
                .total_coverage_amount(BigDecimal.valueOf(1000.00))
                .coverages(Map.of("coverage1", BigDecimal.valueOf(500.00)))
                .assistances(List.of("assistance1"))
                .customer(customer)
                .build();

        var violations = validator.validate(quoteResponse);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMaskTotalMonthlyPremiumAmount() {
        QuoteResponse quoteResponse = QuoteResponse.builder()
                .total_monthly_premium_amount(BigDecimal.valueOf(100.00))
                .build();

        assertEquals("****", quoteResponse.maskTotalMonthlyPremiumAmount());

        quoteResponse.setTotal_monthly_premium_amount(null);
        assertEquals(null, quoteResponse.maskTotalMonthlyPremiumAmount());
    }
}