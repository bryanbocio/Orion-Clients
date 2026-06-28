package com.oriontek.clients.command.service;

import java.util.UUID;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oriontek.clients.command.domain.Address;
import com.oriontek.clients.command.domain.Client;
import com.oriontek.clients.command.dto.AddAddressRequest;
import com.oriontek.clients.command.repository.IAddressRepository;
import com.oriontek.clients.command.repository.IClientRepository;
import com.oriontek.clients.outbox.OutboxEvent;
import com.oriontek.clients.outbox.OutboxRepository;
import com.oriontek.clients.shared.event.EventType;
import com.oriontek.clients.shared.event.IClientEvent;
import com.oriontek.clients.shared.exceptions.ConflictException;
import com.oriontek.clients.shared.exceptions.NotFoundException;

import jakarta.transaction.Transactional;

@Service
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


     @Transactional
    public Client updateClient(UUID id, String name) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client was not found: " + id));
        client.rename(name);
        clientRepository.save(client);
        return client;
    }


      @Transactional
    public void deleteClient(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client was not found: " + id));
        clientRepository.delete(client);
    }


      @Transactional
    public Address addAddress(UUID clientId, AddAddressRequest request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client was not found: " + clientId));

        if (addressRepository.existsByClientIdAndStreetAndCity(clientId, request.street(), request.city())) {
            throw new ConflictException("The address already exists for this client");
        }

        Address address = new Address(
                UUID.randomUUID(), client, request.street(), request.city(), request.country());
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public void removeAddress(UUID clientId, UUID addressId) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client was not found: " + clientId);
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address was not found: " + addressId));
        if (!address.getClient().getId().equals(clientId)) {
            throw new NotFoundException("The address does not belong to the client " + clientId);
        }
        addressRepository.delete(address);
    }

     private void appendEvent(IClientEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            outboxRepository.save(new OutboxEvent(event.clientId(), EventType.of(event), payload));
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("No se pudo serializar el evento " + event, ex);
        }
    }

}
