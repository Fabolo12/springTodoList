package org.example.springprojecttodo.repository;

import org.example.springprojecttodo.model.Client;

import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);

    Optional<Client> findByEmail(String email);
}
