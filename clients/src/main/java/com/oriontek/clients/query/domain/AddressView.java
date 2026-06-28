package com.oriontek.clients.query.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "address_view")

public class AddressView {
    
    @Id
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(nullable = false, length = 255)
    private String street;

    @Column(length = 120)
    private String city;

    @Column(length = 120)
    private String country;

    protected AddressView() {
    }

    public AddressView(UUID id, UUID clientId, String street, String city, String country) {
        this.id = id;
        this.clientId = clientId;
        this.street = street;
        this.city = city;
        this.country = country;
    }

     public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


}
