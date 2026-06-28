package com.oriontek.clients.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddAddressRequest( @NotBlank(message = "The street is required")
        @Size(max = 255)
        String street,

        @Size(max = 120)
        String city,

        @Size(max = 120)
        String country) {
    
}
