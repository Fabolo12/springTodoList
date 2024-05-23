package org.example.springprojecttodo.repository;

import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class ClientRepositoryMap implements ClientRepository {
    private final Map<UUID, Client> db = new HashMap<>();

    @Override
    public Client getClientById(final UUID id) {
        return Objects.requireNonNull(db.get(id), "Client not found with id: " + id);
    }

    @Override
    public Optional<Client> findByEmail(final String email) {
        return db.values().stream()
                .filter(client -> client.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Stream<Client> getAll() {
        return db.values().stream();
    }

    @Override
    public void save(final Client client) {
        db.put(client.getId(), client);
    }

    @Override
    public void update(final Client client) {
        db.put(client.getId(), client);
    }

    @Override
    public void delete(final UUID id) {
        db.values().stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .ifPresent(client ->
                        System.out.printf("Client with id %s change status to %s%n",
                                client.getId(), ClientStatus.INACTIVE
                        ));
    }
}
