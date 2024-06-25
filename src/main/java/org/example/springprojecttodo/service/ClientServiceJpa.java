package org.example.springprojecttodo.service;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.example.springprojecttodo.annotation.LogTime;
import org.example.springprojecttodo.bean.LogInRequestBean;
import org.example.springprojecttodo.bean.SignUpRequestBean;
import org.example.springprojecttodo.exeption.EntityNotFound;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.example.springprojecttodo.repository.ClientRepositorySpring;
import org.example.springprojecttodo.service.creator.ClientCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Base64;
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

    public int countClients(final ClientStatus status) {
        return repository.countClients(status);
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

    @Override
    public Flux<Client> getAllReactive() {
//        ReactiveCrudRepository
        return Flux.fromIterable(repository.findAll());
    }

    public UUID createUser(final SignUpRequestBean signUpRequestBean) {
        final Client client = Client.builder()
                .email(signUpRequestBean.email())
                .password(Base64.getEncoder().encodeToString(signUpRequestBean.password().getBytes()))
                .name(signUpRequestBean.name())
                .status(ClientStatus.ACTIVE)
                .build();
        repository.save(client);
        return client.getId();
    }


    public boolean checkLoginAccess(final LogInRequestBean logInRequestBean) {
        final String password = Base64.getEncoder().encodeToString(logInRequestBean.password().getBytes());
        return repository.findByEmail(logInRequestBean.email())
                .map(Client::getPassword)
                .map(password::equals)
                .orElse(false);
    }

    public Cookie createCookie(final String email) {
        return new Cookie("user", email);
    }

    public Cookie clearCookie() {
        final Cookie user = new Cookie("user", null);
        user.setMaxAge(0);
        return user;
    }
}
