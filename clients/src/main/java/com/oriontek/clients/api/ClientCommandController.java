package com.oriontek.clients.api;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oriontek.clients.command.dto.ClientResponse;
import com.oriontek.clients.command.dto.CreateClientRequest;
import com.oriontek.clients.command.dto.UpdatedClientRequest;
import com.oriontek.clients.command.service.ClientCommandService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/v1/clients")
public class ClientCommandController {
    
    private final String BASE_ENDPOINT_URL="/api/v1/clients/";

    private final ClientCommandService service;

    public ClientCommandController(ClientCommandService service) {
        this.service=service;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create( @Valid @RequestBody CreateClientRequest request ){
        ClientResponse body = ClientResponse.from(service.createClient(request.name()));
        return ResponseEntity.created(URI.create(this.BASE_ENDPOINT_URL + body.id())).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable UUID id,@Valid @RequestBody UpdatedClientRequest request){
        ClientResponse body= ClientResponse.from(service.updateClient(id, request.name()));
        return ResponseEntity.created(URI.create(BASE_ENDPOINT_URL+body.id())).body(body);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.deleteClient(id);

    }
    
}
