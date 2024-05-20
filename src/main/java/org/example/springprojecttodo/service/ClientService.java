package org.example.springprojecttodo.service;

import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository repository;

    @Autowired
    ClientService(final ClientRepository repository) {
        this.repository = repository;
    }

    @LogTime
    public void createAdmin() {
        final Client admin = Client.builder()
                .id(UUID.randomUUID())
                .email("admin@gmail.com")
                .password("root")
                .name("Admin")
                .build();
        repository.save(admin);
    }

    @LogTime
    public Client findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }
}
