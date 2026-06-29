package com.oriontek.clients.command.api;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oriontek.clients.command.dto.AddAddressRequest;
import com.oriontek.clients.command.dto.AddressResponse;
import com.oriontek.clients.command.service.ClientCommandService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients/{clientId}/addresses")
public class AddressCommandController {
      private final ClientCommandService service;

    public AddressCommandController(ClientCommandService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> add(@PathVariable UUID clientId,
                                               @Valid @RequestBody AddAddressRequest request) {
        AddressResponse body = AddressResponse.from(service.addAddress(clientId, request));
        return ResponseEntity
                .created(URI.create("/api/v1/clients/" + clientId + "/addresses/" + body.id()))
                .body(body);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID clientId, @PathVariable UUID addressId) {
        service.removeAddress(clientId, addressId);
    }
}
