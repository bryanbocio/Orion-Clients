package com.oriontek.clients.config;
 
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    public static final String TOPIC = "client-events";
    public static final String DLT = "client-events.DLT";

    @Bean
    public NewTopic clientEventsTopic() {
        return TopicBuilder.name(TOPIC).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic clientEventsDlt() {
        return TopicBuilder.name(DLT).partitions(3).replicas(1).build();
    }

  
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> deadLetterTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(deadLetterTemplate);
        return new DefaultErrorHandler(recoverer, new FixedBackOff(1_000L, 3L));
    }
}
