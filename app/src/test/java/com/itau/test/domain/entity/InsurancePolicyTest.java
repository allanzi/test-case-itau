package com.itau.test.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InsurancePolicyTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidInsurancePolicy() {
        // Arrange
        InsurancePolicy policy = InsurancePolicy.builder()
                .insurance_policy_id("12345")
                .quote_id(1l)
                .created_at("2023-09-03")
                .build();

        // Act
        Set<ConstraintViolation<InsurancePolicy>> violations = validator.validate(policy);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidInsurancePolicy_MissingFields() {
        // Arrange
        InsurancePolicy policy = new InsurancePolicy();

        // Act
        Set<ConstraintViolation<InsurancePolicy>> violations = validator.validate(policy);

        // Assert
        assertEquals(2, violations.size());

        for (ConstraintViolation<InsurancePolicy> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "insurance_policy_id":
                    assertEquals("insurance_policy_id is mandatory", message);
                    break;
                case "quote_id":
                    assertEquals("quote_id is mandatory", message);
                    break;
                case "created_at":
                    assertEquals("created_at is mandatory", message);
                    break;
            }
        }
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        InsurancePolicy policy = new InsurancePolicy();

        // Act
        policy.setInsurance_policy_id("12345");
        policy.setQuote_id(67890L);
        policy.setCreated_at("2023-09-03");

        // Assert
        assertEquals("12345", policy.getInsurance_policy_id());
        assertEquals(67890L, policy.getQuote_id());
        assertEquals("2023-09-03", policy.getCreated_at());
    }
}