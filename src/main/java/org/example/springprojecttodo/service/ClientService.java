package org.example.springprojecttodo.service;

import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.repository.ClientRepository;
import org.example.springprojecttodo.service.creator.ClientCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Profile("!spring-data")
@Service
public class ClientService implements ClientServiceI {

    private final ClientRepository repository;

    private final ClientCreator clientCreator;

    @Autowired
    ClientService(
            final ClientRepository repository,
            final ClientCreator clientCreator
    ) {
        this.repository = repository;
        this.clientCreator = clientCreator;
    }

    @LogTime
//    @Transactional
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

    public void deleteClient(UUID id) {
        repository.delete(id);
    }

    public void updateClient(Client client) {
        repository.update(client);
    }

    public Client getClient(UUID id) {
        return repository.getClientById(id);
    }

    public Stream<Client> getAll() {
        return repository.getAll();
    }

    @Override
    public Stream<Client> getAll(final Sort sort) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Stream<Client> getAll(final Pageable pageable) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
