package org.example.springprojecttodo.service;

import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.model.Client;

import java.util.UUID;
import java.util.stream.Stream;

public interface ClientServiceI {
    Client createAdmin();

    @LogTime
    Client findByEmail(String email);

    int countClients();

    void deleteClient(UUID id);

    void updateClient(Client client);

    Client getClient(UUID id);

    Stream<Client> getAll();
}
