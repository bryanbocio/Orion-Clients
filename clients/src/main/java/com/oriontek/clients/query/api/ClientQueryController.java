package com.oriontek.clients.query.api;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oriontek.clients.query.dto.AddressViewResponse;
import com.oriontek.clients.query.dto.ClientViewResponse;
import com.oriontek.clients.query.repository.IAddressViewRepository;
import com.oriontek.clients.query.repository.IClientViewRepository;
import com.oriontek.clients.shared.exceptions.NotFoundException;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientQueryController {
    
    private final IClientViewRepository clientViewRepository;
    private final IAddressViewRepository addressViewRepository;

     public ClientQueryController(IClientViewRepository clientViewRepository,
                                 IAddressViewRepository addressViewRepository) {
        this.clientViewRepository = clientViewRepository;
        this.addressViewRepository = addressViewRepository;
    }

    @GetMapping
    @Transactional(transactionManager = "readTx", readOnly = true)
    public List<ClientViewResponse> list() {
        return clientViewRepository.findAll().stream().map(ClientViewResponse::from).toList();
    }

     @GetMapping("/{id}")
    @Transactional(transactionManager = "readTx", readOnly = true)
    public ClientViewResponse getById(@PathVariable UUID id) {
        return clientViewRepository.findById(id)
                .map(ClientViewResponse::from)
                .orElseThrow(() -> new NotFoundException("Client was not found: " + id));
    }

    @GetMapping("/{id}/addresses")
    @Transactional(transactionManager = "readTx", readOnly = true)
    public List<AddressViewResponse> addresses(@PathVariable UUID id) {
        if (!clientViewRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado: " + id);
        }
        return addressViewRepository.findByClientId(id).stream().map(AddressViewResponse::from).toList();
    }
}
