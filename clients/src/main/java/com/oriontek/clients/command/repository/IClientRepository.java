package com.oriontek.clients.command.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oriontek.clients.command.domain.Client;

public interface IClientRepository extends JpaRepository<Client, UUID> {
    
    
}
