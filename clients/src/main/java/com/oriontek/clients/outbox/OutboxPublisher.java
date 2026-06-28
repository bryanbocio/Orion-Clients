package com.oriontek.clients.outbox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oriontek.clients.config.KafkaConfig;
import com.oriontek.clients.shared.event.IClientEvent;

import jakarta.transaction.Transactional;

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

    @Scheduled(fixedDelayString = "${outbox.poll-delay-ms:500}")
    @Transactional
    public void publishPending() {

        List<OutboxEvent> events = this.outboxRepository
                .findByPublishedFalseOrderByCreatedAtAsc(PageRequest.of(0, batchSize));

        if (events.isEmpty()) {
            return;
        }

        for (OutboxEvent event : events) {
            try {

                IClientEvent payload = objectMapper.readValue(event.getPayload(), event.getType().eventClass());
                this.kafkaTemplate.send(KafkaConfig.TOPIC, event.getAggregateId(),payload);
                event.markPublished();

            } catch (Exception ex) {
                 log.error("The event can not be published {} ({}): {}",
                        event.getId(), event.getType(), ex.getMessage());
            }
        }
    }
}
