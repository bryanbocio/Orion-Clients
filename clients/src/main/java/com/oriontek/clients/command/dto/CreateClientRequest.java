package com.oriontek.clients.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
        @NotBlank(message = "The name is required")
        @Size(max = 150, message = "lenght can not be more than 150")
        String name
) {
    
}
