package com.oriontek.clients.outbox;

import java.time.Instant;
import java.util.UUID;

import com.oriontek.clients.shared.event.EventType;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "outbox_event")
public class OutboxEvent {
    @Id
    private UUID id;

    @Column(name = "aggregate_id", nullable = false, length = 64)
    private String aggregateId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 64)
    private EventType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private String payload;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private boolean published;

    protected OutboxEvent() {
    }

    public OutboxEvent(UUID aggregateId, EventType type, String payload) {
        this.id = UUID.randomUUID();
        this.aggregateId = aggregateId.toString();
        this.type = type;
        this.payload = payload;
        this.createdAt = Instant.now();
        this.published = false;
    }

    public void markPublished() {
        this.published = true;
    }

    public UUID getId() {
        return id;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public EventType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isPublished() {
        return published;
    }

}
