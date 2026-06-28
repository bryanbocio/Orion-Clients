package com.oriontek.clients.query.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_view")
public class ClientView {
    @Id
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "address_count", nullable = false)
    private int addressCount;

    protected ClientView() {
    }

    public ClientView(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.addressCount = 0;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void incrementAddressCount() {
        this.addressCount++;
    }

    public void decrementAddressCount() {
        if (this.addressCount > 0) {
            this.addressCount--;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAddressCount() {
        return addressCount;
    }
}
