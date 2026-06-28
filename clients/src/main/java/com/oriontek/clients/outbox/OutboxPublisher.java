package com.oriontek.clients.outbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OutboxPublisher {
        private static final Logger log = LoggerFactory.getLogger(OutboxPublisher.class);

         private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final int batchSize;
    public OutboxPublisher(OutboxRepository outboxRepository,
                           KafkaTemplate<String, Object> kafkaTemplate,
                           ObjectMapper objectMapper,
                           @org.springframework.beans.factory.annotation.Value("${outbox.batch-size:100}") int batchSize) {
    
                              this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.batchSize = batchSize;
                        }
}
