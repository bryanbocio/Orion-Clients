package com.oriontek.clients.query.dto;

import java.util.UUID;

import com.oriontek.clients.query.domain.ClientView;

public record ClientViewResponse(UUID id, String name, int addressCount) {
            
    public static ClientViewResponse from(ClientView view){
        return new ClientViewResponse(view.getId(), view.getName(), view.getAddressCount());
    }
    
}
