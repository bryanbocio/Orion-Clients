package com.oriontek.clients.command.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatedClientRequest (@NotNull UUID id, @NotBlank String name){
    
}
