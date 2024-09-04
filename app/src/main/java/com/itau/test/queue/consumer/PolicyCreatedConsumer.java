package com.itau.test.queue.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.itau.test.domain.entity.InsurancePolicy;
import com.itau.test.queue.producer.PolicyUnprocessableProducer;
import com.itau.test.service.QuoteService;
import com.itau.test.util.QueueUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PolicyCreatedConsumer {
    private final QuoteService quoteService;
    private final PolicyUnprocessableProducer policyUnprocessableProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyCreatedConsumer.class);

    @RabbitListener(queues = "${queue.policyCreated.name}")
    public void receive(String message) {
        try {
            InsurancePolicy policy = QueueUtil.stringToObject(message, InsurancePolicy.class);
            quoteService.updatePolicy(policy);
            LOGGER.info("PolicyCreatedConsumer processed message: {}", message);
        } catch (Exception e) {
            LOGGER.warn("PolicyCreatedConsumer error '{}', to process message: {}", e.getMessage(), message);
            try {
                policyUnprocessableProducer.send(message);
            } catch (Exception ex) {
                LOGGER.warn("PolicyCreatedConsumer error send to policyUnprocessableProducer: {}, message: {}", e.getMessage(), message);
            }
        }
    }
}