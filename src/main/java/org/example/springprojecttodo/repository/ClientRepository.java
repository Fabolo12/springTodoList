package org.example.springprojecttodo.repository;

import org.example.springprojecttodo.model.Client;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface ClientRepository {
    Client getClientById(UUID id);

    Optional<Client> findByEmail(String email);

    Stream<Client> getAll();

    void save(Client client);

    void update(Client client);

    void delete(UUID id);
}
