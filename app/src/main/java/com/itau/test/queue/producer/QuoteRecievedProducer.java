package com.itau.test.queue.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuoteRecievedProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.quoteRecieved.name}")
    private String queue;

    @Value("${queue.quoteRecieved.exchange}")
    private String exchange;

    @Value("${queue.quoteRecieved.routingKey}")
    private String routingKey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
