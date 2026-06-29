package com.oriontek.clients.query.dto;

import java.util.UUID;

import com.oriontek.clients.query.domain.AddressView;

public record AddressViewResponse(UUID id, UUID clientId, String street, String city, String country) {
    
      public static AddressViewResponse from(AddressView view) {
        return new AddressViewResponse(
                view.getId(), view.getClientId(), view.getStreet(), view.getCity(), view.getCountry());
    }
}
