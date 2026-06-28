package com.oriontek.clients.command.dto;

import java.util.UUID;

import com.oriontek.clients.command.domain.Client;

public record ClientResponse(UUID id, String name){

    public static ClientResponse from(Client client){
        return new ClientResponse(client.getId(),    client.getName());
    }
}