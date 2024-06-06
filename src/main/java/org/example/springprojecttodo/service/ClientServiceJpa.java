package org.example.springprojecttodo.service;

import jakarta.transaction.Transactional;
import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.exeption.EntityNotFound;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.repository.ClientRepositorySpring;
import org.example.springprojecttodo.service.creator.ClientCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Profile("spring-data")
@Service
public class ClientServiceJpa implements ClientServiceI {
    private final ClientRepositorySpring repository;

    private final ClientCreator clientCreator;

    @Autowired
    ClientServiceJpa(
            final ClientRepositorySpring repository,
            final ClientCreator clientCreator
    ) {
        this.repository = repository;
        this.clientCreator = clientCreator;
    }

    @LogTime
    @Transactional
    public Client createAdmin() {
        final Client admin = clientCreator.createClient();
        repository.save(admin);
        return admin;
    }

    @LogTime
    public Client findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public int countClients() {
        return repository.countClients();
    }

    @Transactional
    public void deleteClient(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFound("Client/id: " + id);
        }

        repository.delete(id);
    }

    public void updateClient(Client client) {
        repository.save(client);
    }

    public Client getClient(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public Stream<Client> getAll() {
        return repository.findAll().stream();
    }

    @Override
    public Stream<Client> getAll(final Sort sort) {
        return repository.findAll(sort).stream();
    }

    @Override
    public Stream<Client> getAll(final Pageable pageable) {
        return repository.findAll(pageable).stream();
    }
}
