package com.itau.test.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    @Value("${queue.quoteRecieved.name}")
    private String quoteRecieved;

    @Value("${queue.policyCreated.name}")
    private String policyCreated;

    @Value("${queue.policyUnprocessable.name}")
    private String policyUnprocessable;

    @Bean
    public Queue quoteRecievedQueue() {
        return new Queue(quoteRecieved, true);
    }

    @Bean
    public Queue policyCreatedQueue() {
        return new Queue(policyCreated, true);
    }

    @Bean
    public Queue policyUnprocessableQueue() {
        return new Queue(policyUnprocessable, true);
    }
}
