package com.itau.test.domain.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerResponseTest {

    @Test
    void testNoArgsConstructor() {
        CustomerResponse customerResponse = new CustomerResponse();
        assertNull(customerResponse.getDocument_number());
        assertNull(customerResponse.getName());
        assertNull(customerResponse.getType());
        assertNull(customerResponse.getGender());
        assertNull(customerResponse.getDate_of_birth());
        assertNull(customerResponse.getEmail());
        assertNull(customerResponse.getPhone_number());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        CustomerResponse customerResponse = new CustomerResponse("123456789", "John Doe", "Individual", "Male", dateOfBirth, "john.doe@example.com", 1234567890L);

        assertEquals("123456789", customerResponse.getDocument_number());
        assertEquals("John Doe", customerResponse.getName());
        assertEquals("Individual", customerResponse.getType());
        assertEquals("Male", customerResponse.getGender());
        assertEquals(dateOfBirth, customerResponse.getDate_of_birth());
        assertEquals("john.doe@example.com", customerResponse.getEmail());
        assertEquals(1234567890L, customerResponse.getPhone_number());
    }

    @Test
    void testBuilder() {
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        CustomerResponse customerResponse = CustomerResponse.builder()
                .document_number("123456789")
                .name("John Doe")
                .type("Individual")
                .gender("Male")
                .date_of_birth(dateOfBirth)
                .email("john.doe@example.com")
                .phone_number(1234567890L)
                .build();

        assertEquals("123456789", customerResponse.getDocument_number());
        assertEquals("John Doe", customerResponse.getName());
        assertEquals("Individual", customerResponse.getType());
        assertEquals("Male", customerResponse.getGender());
        assertEquals(dateOfBirth, customerResponse.getDate_of_birth());
        assertEquals("john.doe@example.com", customerResponse.getEmail());
        assertEquals(1234567890L, customerResponse.getPhone_number());
    }

    @Test
    void testJsonSerialization() throws Exception {
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        CustomerResponse customerResponse = CustomerResponse.builder()
                .document_number("123456789")
                .name("John Doe")
                .type("Individual")
                .gender("Male")
                .date_of_birth(dateOfBirth)
                .email("john.doe@example.com")
                .phone_number(1234567890L)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        String jsonString = objectMapper.writeValueAsString(customerResponse);

        String expectedJson = "{\"document_number\":\"123456789\",\"name\":\"John Doe\",\"type\":\"Individual\",\"gender\":\"Male\",\"date_of_birth\":\"1990-01-01\",\"email\":\"john.doe@example.com\",\"phone_number\":1234567890}";
        assertEquals(expectedJson, jsonString);
    }

    @Test
    void testJsonDeserialization() throws Exception {
        String jsonString = "{\"document_number\":\"123456789\",\"name\":\"John Doe\",\"type\":\"Individual\",\"gender\":\"Male\",\"date_of_birth\":\"1990-01-01\",\"email\":\"john.doe@example.com\",\"phone_number\":1234567890}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        CustomerResponse customerResponse = objectMapper.readValue(jsonString, CustomerResponse.class);

        assertEquals("123456789", customerResponse.getDocument_number());
        assertEquals("John Doe", customerResponse.getName());
        assertEquals("Individual", customerResponse.getType());
        assertEquals("Male", customerResponse.getGender());
        assertEquals(LocalDate.of(1990, 1, 1), customerResponse.getDate_of_birth());
        assertEquals("john.doe@example.com", customerResponse.getEmail());
        assertEquals(1234567890L, customerResponse.getPhone_number());
    }
}