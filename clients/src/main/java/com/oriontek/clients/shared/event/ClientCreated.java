package com.oriontek.clients.shared.event;

import java.util.UUID;

public record
 ClientCreated(UUID clientId, String name) implements IClientEvent {
    
}
