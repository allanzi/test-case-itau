package com.itau.test.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionResponseTest {

    private ExceptionResponse exceptionResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_CODE", "Error message", System.currentTimeMillis(), null);
    }

    @Test
    public void testGetStatus() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exceptionResponse.getStatus());
    }

    @Test
    public void testGetCode() {
        assertEquals("ERROR_CODE", exceptionResponse.getCode());
    }

    @Test
    public void testGetMessage() {
        assertEquals("Error message", exceptionResponse.getMessage());
    }

    @Test
    public void testGetTimestamp() {
        long timestamp = System.currentTimeMillis();
        exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_CODE", "Error message", timestamp, null);
        assertEquals(timestamp, exceptionResponse.getTimestamp());
    }

    @Test
    public void testSetStatus() {
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST, exceptionResponse.getStatus());
    }

    @Test
    public void testSetCode() {
        exceptionResponse.setCode("NEW_ERROR_CODE");
        assertEquals("NEW_ERROR_CODE", exceptionResponse.getCode());
    }

    @Test
    public void testSetMessage() {
        exceptionResponse.setMessage("New error message");
        assertEquals("New error message", exceptionResponse.getMessage());
    }

    @Test
    public void testSetTimestamp() {
        long newTimestamp = System.currentTimeMillis();
        exceptionResponse.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, exceptionResponse.getTimestamp());
    }
}