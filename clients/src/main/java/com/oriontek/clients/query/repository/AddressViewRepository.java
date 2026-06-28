package com.oriontek.clients.query.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oriontek.clients.query.domain.AddressView;

public interface AddressViewRepository extends JpaRepository<AddressView, UUID> {
    List<AddressView> findByClientId(UUID clientId);

    void deleteByClientId(UUID clientId);

}
