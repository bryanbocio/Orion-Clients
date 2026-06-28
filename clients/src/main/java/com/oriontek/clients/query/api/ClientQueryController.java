package com.oriontek.clients.query.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oriontek.clients.query.repository.IAddressViewRepository;
import com.oriontek.clients.query.repository.IClientViewRepository;

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
}
