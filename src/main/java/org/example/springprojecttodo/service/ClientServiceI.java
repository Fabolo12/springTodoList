package org.example.springprojecttodo.service;

import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;
import java.util.stream.Stream;

public interface ClientServiceI {
    Client createAdmin();

    @LogTime
    Client findByEmail(String email);

    int countClients();

    default int countClients(ClientStatus status) {
        return 0;
    };

    void deleteClient(UUID id);

    void updateClient(Client client);

    Client getClient(UUID id);

    Stream<Client> getAll();

    Stream<Client> getAll(Sort sort);

    Stream<Client> getAll(Pageable pageable);
}
