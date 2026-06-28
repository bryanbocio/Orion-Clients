package com.oriontek.clients.shared.event;

import java.util.UUID;

public record AddressAdded(  UUID clientId,
        UUID addressId,
        String street,
        String city,
        String country) implements IClientEvent {
}