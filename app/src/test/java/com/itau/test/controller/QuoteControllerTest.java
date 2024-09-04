package com.itau.test.controller;

import com.itau.test.domain.request.QuoteRequest;
import com.itau.test.domain.response.QuoteResponse;
import com.itau.test.queue.producer.QuoteRecievedProducer;
import com.itau.test.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuoteControllerTest {

    @Mock
    private QuoteRecievedProducer quoteRecievedProducer;

    @Mock
    private QuoteService service;

    @InjectMocks
    private QuoteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void testGetQuotes() {
        // Arrange
        List<QuoteResponse> mockResponse = List.of(new QuoteResponse());
        when(service.getQuotes()).thenReturn(mockResponse);

        // Act
        ResponseEntity<Map<String, List<QuoteResponse>>> response = controller.getQuotes();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.singletonMap("data", mockResponse), response.getBody());
        verify(service, times(1)).getQuotes();
    }

    @Test
    void testRequestQuote_Success() {
        // Arrange
        QuoteRequest mockRequest = new QuoteRequest();
        QuoteResponse mockResponse = new QuoteResponse();
        when(service.requestQuote(mockRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<QuoteResponse> response = controller.requestQuote(mockRequest);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(service, times(1)).requestQuote(mockRequest);
        verify(quoteRecievedProducer, times(1)).send(anyString());
    }

    @Test
    void testRequestQuote_ExceptionWhileSendingToQueue() {
        // Arrange
        QuoteRequest mockRequest = new QuoteRequest();
        QuoteResponse mockResponse = new QuoteResponse();
        when(service.requestQuote(mockRequest)).thenReturn(mockResponse);
        doThrow(new RuntimeException("Queue Error")).when(quoteRecievedProducer).send(anyString());

        // Act
        ResponseEntity<QuoteResponse> response = controller.requestQuote(mockRequest);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(service, times(1)).requestQuote(mockRequest);
        verify(quoteRecievedProducer, times(1)).send(anyString());
    }
}
