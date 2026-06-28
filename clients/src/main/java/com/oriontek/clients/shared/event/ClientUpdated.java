package com.oriontek.clients.shared.event;

import java.util.UUID;

public record ClientUpdated(UUID clientId, String name) implements IClientEvent {
    
}
