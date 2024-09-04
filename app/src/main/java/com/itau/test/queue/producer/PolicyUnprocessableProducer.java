package com.itau.test.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PolicyUnprocessableProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.policyUnprocessable.name}")
    private String queue;

    @Value("${queue.policyUnprocessable.exchange}")
    private String exchange;

    @Value("${queue.policyUnprocessable.routingKey}")
    private String routingKey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
