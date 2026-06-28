package com.oriontek.clients.shared.event;

import java.util.UUID;

public sealed  interface IClientEvent permits ClientCreated, ClientDeleted, ClientUpdated, AddressAdded, AddressRemoved {
     UUID clientId();
}
