package com.oriontek.clients.query.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oriontek.clients.config.KafkaConfig;
import com.oriontek.clients.query.domain.AddressView;
import com.oriontek.clients.query.domain.ClientView;
import com.oriontek.clients.query.repository.IAddressViewRepository;
import com.oriontek.clients.query.repository.IClientViewRepository;
import com.oriontek.clients.shared.event.AddressAdded;
import com.oriontek.clients.shared.event.AddressRemoved;
import com.oriontek.clients.shared.event.ClientCreated;
import com.oriontek.clients.shared.event.ClientDeleted;
import com.oriontek.clients.shared.event.ClientUpdated;
import com.oriontek.clients.shared.event.IClientEvent;


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


    @KafkaListener(topics = KafkaConfig.TOPIC, groupId = "oriontek-query")
    @Transactional(transactionManager = "readTx")
    public void on(IClientEvent event) {
        log.info("Read-model recibe evento: {}", event.getClass().getSimpleName());
        switch (event) {
            case ClientCreated e -> onClientCreated(e);
            case ClientUpdated e -> onClientUpdated(e);
            case ClientDeleted e -> onClientDeleted(e);
            case AddressAdded e -> onAddressAdded(e);
            case AddressRemoved e -> onAddressRemoved(e);
        }
    }

     private void onClientCreated(ClientCreated e) {
        ClientView view = clientViewRepository.findById(e.clientId())
                .orElseGet(() -> new ClientView(e.clientId(), e.name()));
        view.rename(e.name());
        clientViewRepository.save(view);
    }

    private void onClientUpdated(ClientUpdated e) {
        ClientView view = clientViewRepository.findById(e.clientId())
                .orElseGet(() -> new ClientView(e.clientId(), e.name())); 
        view.rename(e.name());
        clientViewRepository.save(view);
    }

    private void onClientDeleted(ClientDeleted e) {
        addressViewRepository.deleteByClientId(e.clientId());
        clientViewRepository.deleteById(e.clientId());
    }

    private void onAddressAdded(AddressAdded e) {
        if (addressViewRepository.existsById(e.addressId())) {
            return;
        }
        addressViewRepository.save(
                new AddressView(e.addressId(), e.clientId(), e.street(), e.city(), e.country()));
        clientViewRepository.findById(e.clientId()).ifPresent(view -> {
            view.incrementAddressCount();
            clientViewRepository.save(view);
        });
    }

    private void onAddressRemoved(AddressRemoved e) {
        if (!addressViewRepository.existsById(e.addressId())) {
            return; 
        }
        addressViewRepository.deleteById(e.addressId());
        clientViewRepository.findById(e.clientId()).ifPresent(view -> {
            view.decrementAddressCount();
            clientViewRepository.save(view);
        });
    }

}
