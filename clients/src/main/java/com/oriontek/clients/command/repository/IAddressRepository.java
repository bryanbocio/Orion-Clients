package com.oriontek.clients.command.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oriontek.clients.command.domain.Address;

public interface IAddressRepository extends JpaRepository<Address, UUID> {
    
}
