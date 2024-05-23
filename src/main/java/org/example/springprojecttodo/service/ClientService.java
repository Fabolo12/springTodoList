package org.example.springprojecttodo.service;

import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.example.springprojecttodo.repository.ClientRepository;
import org.example.springprojecttodo.repository.ClientRepositoryJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ClientService {

    private final ClientRepositoryJdbc repository;

    @Autowired
    ClientService(final ClientRepositoryJdbc repository) {
        this.repository = repository;
    }

    @LogTime
    public Client createAdmin() {
        final Client admin = Client.builder()
                .id(UUID.randomUUID())
                .email("admin@gmail.com")
                .password("root")
                .name("Admin")
                .status(ClientStatus.ACTIVE)
                .build();
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
}
