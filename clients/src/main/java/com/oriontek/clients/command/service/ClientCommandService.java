package com.oriontek.clients.command.service;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oriontek.clients.command.domain.Client;
import com.oriontek.clients.command.repository.IAddressRepository;
import com.oriontek.clients.command.repository.IClientRepository;
import com.oriontek.clients.outbox.OutboxRepository;

import jakarta.transaction.Transactional;

public class ClientCommandService {
    
     private final IClientRepository clientRepository;
    private final IAddressRepository addressRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;


    public ClientCommandService(IClientRepository clientRepository, IAddressRepository addressRepository, OutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }


    @Transactional
    public Client createClient(String name) {
        Client client = new Client(UUID.randomUUID(), name);
        clientRepository.save(client);
        return client;
    }
}
