package com.oriontek.clients.shared.event;

public enum EventType {
    

    CLIENT_CREATED(ClientCreated.class),
    CLIENT_UPDATED(ClientUpdated.class),
    CLIENT_DELETED(ClientDeleted.class),
    ADDRESS_ADDED(AddressAdded.class),
    ADDRESS_REMOVED(AddressRemoved.class);
    
    private final Class<? extends IClientEvent> eventClass;
     EventType(Class<? extends IClientEvent> eventClass) {
        this.eventClass = eventClass;
    }

       public Class<? extends IClientEvent> eventClass() {
        return eventClass;
    }

     public static EventType of(IClientEvent event) {
        return switch (event) {
            case ClientCreated  e -> CLIENT_CREATED;
            case ClientUpdated  e -> CLIENT_UPDATED;
            case ClientDeleted  e -> CLIENT_DELETED;
            case AddressAdded   e -> ADDRESS_ADDED;
            case AddressRemoved e -> ADDRESS_REMOVED;
        };
    }

}
