package com.oriontek.clients.query.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oriontek.clients.query.domain.ClientView;

public interface IClientViewRepository extends JpaRepository<ClientView, UUID>{
    
}
