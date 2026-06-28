package com.oriontek.clients.shared.event;

import java.util.UUID;

public record AddressRemoved(UUID clientId, UUID addressId) implements IClientEvent {
    
}
