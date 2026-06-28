package com.oriontek.clients.query.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.oriontek.clients.query.repository.IAddressViewRepository;
import com.oriontek.clients.query.repository.IClientViewRepository;

@Component
public class ClientEventListener {

    private static final Logger log = LoggerFactory.getLogger(ClientEventListener.class);

    private final IClientViewRepository clientViewRepository;
    private final IAddressViewRepository addressViewRepository;

    public ClientEventListener(IClientViewRepository clientViewRepository,
            IAddressViewRepository addressViewRepository) {

        this.clientViewRepository = clientViewRepository;
        this.addressViewRepository = addressViewRepository;

    }
}
