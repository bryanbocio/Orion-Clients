-- write-db (normalizada) — fuente de verdad del lado command
CREATE TABLE client (
    id         UUID PRIMARY KEY,
    name       VARCHAR(150) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE address (
    id        UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES client(id) ON DELETE CASCADE,
    street    VARCHAR(255) NOT NULL,
    city      VARCHAR(120),
    country   VARCHAR(120),
    UNIQUE (client_id, street, city)
);

CREATE TABLE outbox_event (
    id           UUID PRIMARY KEY,
    aggregate_id VARCHAR(64) NOT NULL,   -- clientId (= key de Kafka)
    type         VARCHAR(64) NOT NULL,   -- CLIENT_CREATED, ADDRESS_ADDED, ...
    payload      JSONB NOT NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT now(),
    published    BOOLEAN NOT NULL DEFAULT false
);

CREATE INDEX idx_outbox_unpublished ON outbox_event(published) WHERE published = false;
