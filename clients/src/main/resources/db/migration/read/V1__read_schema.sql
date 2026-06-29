-- read-db (desnormalizada) — optimizada para consultas
CREATE TABLE client_view (
    id            UUID PRIMARY KEY,
    name          VARCHAR(150) NOT NULL,
    address_count INT NOT NULL DEFAULT 0   -- precalculado (ventaja de CQRS)
);

CREATE TABLE address_view (
    id        UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    street    VARCHAR(255) NOT NULL,
    city      VARCHAR(120),
    country   VARCHAR(120)
);

CREATE INDEX idx_address_view_client ON address_view(client_id);
