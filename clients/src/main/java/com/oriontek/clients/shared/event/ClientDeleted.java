package com.oriontek.clients.shared.event;

import java.util.UUID;

public record ClientDeleted(UUID clientId) implements IClientEvent {}