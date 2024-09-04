package com.itau.test.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCustomer() {
        // Arrange
        Customer customer = Customer.builder()
                .document_number("123456789")
                .name("John Doe")
                .type("individual")
                .gender("male")
                .date_of_birth(LocalDate.of(1990, 1, 1))
                .email("john.doe@example.com")
                .phone_number(1234567890L)
                .build();

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidCustomer_MissingFields() {
        // Arrange
        Customer customer = new Customer();

        // Act
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        // Assert
        assertEquals(7, violations.size());

        for (ConstraintViolation<Customer> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "document_number":
                    assertEquals("document_number is mandatory", message);
                    break;
                case "name":
                    assertEquals("name is mandatory", message);
                    break;
                case "type":
                    assertEquals("type is mandatory", message);
                    break;
                case "gender":
                    assertEquals("gender is mandatory", message);
                    break;
                case "date_of_birth":
                    assertEquals("date_of_birth is mandatory", message);
                    break;
                case "email":
                    assertEquals("email is mandatory", message);
                    break;
                case "phone_number":
                    assertEquals("phone_number is mandatory", message);
                    break;
            }
        }
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Customer customer = new Customer();
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);

        // Act
        customer.setDocument_number("123456789");
        customer.setName("John Doe");
        customer.setType("individual");
        customer.setGender("male");
        customer.setDate_of_birth(dateOfBirth);
        customer.setEmail("john.doe@example.com");
        customer.setPhone_number(1234567890L);

        // Assert
        assertEquals("123456789", customer.getDocument_number());
        assertEquals("John Doe", customer.getName());
        assertEquals("individual", customer.getType());
        assertEquals("male", customer.getGender());
        assertEquals(dateOfBirth, customer.getDate_of_birth());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(1234567890L, customer.getPhone_number());
    }
}
