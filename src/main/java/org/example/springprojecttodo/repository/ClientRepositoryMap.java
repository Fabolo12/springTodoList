package org.example.springprojecttodo.repository;

import org.example.springprojecttodo.model.Client;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ClientRepositoryMap implements ClientRepository {
    private Map<String, Client> db = new HashMap<>();

    public Client save(Client client) {
        db.put(client.getEmail(), client);
        return client;
    }

    public Optional<Client> findByEmail(String email) {
        return Optional.ofNullable(db.get(email));
    }
}
