package com.itau.test.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteEntityTest {

    private Validator validator;
    private QuoteEntity quoteEntity;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        quoteEntity = QuoteEntity.builder()
                .id(1L)
                .product_id("product123")
                .offer_id("offer123")
                .category("category123")
                .total_monthly_premium_amount(new BigDecimal("100.00"))
                .total_coverage_amount(new BigDecimal("1000.00"))
                .coverages(Map.of("coverage1", new BigDecimal("500.00")))
                .assistances(List.of("assistance1"))
                .customer(Mockito.mock(Customer.class))
                .policy_id("policy123")
                .policty_created_at("2023-10-01")
                .build();
    }

    @Test
    public void testInvalidProductId() {
        quoteEntity.setProduct_id("");
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidOfferId() {
        quoteEntity.setOffer_id("");
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidCategory() {
        quoteEntity.setCategory("");
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidTotalMonthlyPremiumAmount() {
        quoteEntity.setTotal_monthly_premium_amount(new BigDecimal("0.00"));
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidTotalCoverageAmount() {
        quoteEntity.setTotal_coverage_amount(new BigDecimal("0.00"));
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidCoverages() {
        quoteEntity.setCoverages(Map.of());
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidAssistances() {
        quoteEntity.setAssistances(List.of());
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidCustomer() {
        quoteEntity.setCustomer(null);
        Set<ConstraintViolation<QuoteEntity>> violations = validator.validate(quoteEntity);
        assertFalse(violations.isEmpty());
    }
}