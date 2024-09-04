package com.itau.test.queue.consumer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itau.test.domain.entity.InsurancePolicy;
import com.itau.test.queue.producer.PolicyUnprocessableProducer;
import com.itau.test.service.QuoteService;
import com.itau.test.util.QueueUtil;

public class PolicyCreatedConsumerTest {

    @Mock
    private QuoteService quoteService;

    @Mock
    private PolicyUnprocessableProducer policyUnprocessableProducer;

    @Mock
    private Logger logger;

    @InjectMocks
    private PolicyCreatedConsumer policyCreatedConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceive_SuccessfulProcessing() throws JsonProcessingException {
        // Given
        String message = "{\"insurance_policy_id\":\"1\",\"quote_id\":\"1\",\"created_at\":\"2024-09-02T00:00:00Z\"}";
        QueueUtil.stringToObject(message, InsurancePolicy.class);

        // When
        policyCreatedConsumer.receive(message);

        // Then
        ArgumentCaptor<InsurancePolicy> policyCaptor = ArgumentCaptor.forClass(InsurancePolicy.class);
        verify(quoteService, times(1)).updatePolicy(policyCaptor.capture());
        InsurancePolicy capturedPolicy = policyCaptor.getValue();
        assertEquals("1", capturedPolicy.getInsurance_policy_id());
        assertEquals(1l, capturedPolicy.getQuote_id());
        assertEquals("2024-09-02T00:00:00Z", capturedPolicy.getCreated_at());
    }

    @Test
    public void testReceive_ExceptionDuringProcessing() throws Exception {
        // Given
        String message = "{\"insurance_policy_id\":\"1\",\"quote_id\":\"1\",\"created_at\":\"2024-09-02T00:00:00Z\"}";
        InsurancePolicy policy = QueueUtil.stringToObject(message, InsurancePolicy.class);

        doThrow(new RuntimeException("Processing error")).when(quoteService).updatePolicy(policy);

        // When
        policyCreatedConsumer.receive(message);

        // Then
        ArgumentCaptor<InsurancePolicy> policyCaptor = ArgumentCaptor.forClass(InsurancePolicy.class);
        verify(quoteService, times(1)).updatePolicy(policyCaptor.capture());
        InsurancePolicy capturedPolicy = policyCaptor.getValue();
        assertEquals("1", capturedPolicy.getInsurance_policy_id());
        assertEquals(1l, capturedPolicy.getQuote_id());
        assertEquals("2024-09-02T00:00:00Z", capturedPolicy.getCreated_at());
    }

    @Test
    public void testReceive_ExceptionDuringSendingToPolicyUnprocessableProducer() throws Exception {
        // Given
        String message = "{\"insurance_policy_id\":\"1\",\"quote_id\":\"1\",\"created_at\":\"2024-09-02T00:00:00Z\"}";
        InsurancePolicy policy = QueueUtil.stringToObject(message, InsurancePolicy.class);

        doThrow(new RuntimeException("Processing error")).when(quoteService).updatePolicy(policy);
        doThrow(new RuntimeException("Sending error")).when(policyUnprocessableProducer).send(message);

        // When
        policyCreatedConsumer.receive(message);

        // Then
        ArgumentCaptor<InsurancePolicy> policyCaptor = ArgumentCaptor.forClass(InsurancePolicy.class);
        verify(quoteService, times(1)).updatePolicy(policyCaptor.capture());
        InsurancePolicy capturedPolicy = policyCaptor.getValue();
        assertEquals("1", capturedPolicy.getInsurance_policy_id());
        assertEquals(1l, capturedPolicy.getQuote_id());
        assertEquals("2024-09-02T00:00:00Z", capturedPolicy.getCreated_at());
    }
}