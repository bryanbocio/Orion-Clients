package com.oriontek.clients.command.dto;

import java.util.UUID;

import com.oriontek.clients.command.domain.Address;

public record AddressResponse(UUID id, UUID clientId, String street, String city, String country) {

    public static AddressResponse from(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getClient().getId(),
                address.getStreet(),
                address.getCity(),
                address.getCountry());
    }
}
