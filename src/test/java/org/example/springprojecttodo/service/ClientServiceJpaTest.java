package org.example.springprojecttodo.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.example.springprojecttodo.SpringProjectTodoApplication;
import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.example.springprojecttodo.repository.ClientRepositorySpring;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

@Rollback
@Transactional
@SpringBootTest(classes = SpringProjectTodoApplication.class)
class ClientServiceJpaTest {

    private final ClientServiceI clientService;

    private final ClientRepositorySpring repository;

    @Autowired
    ClientServiceJpaTest(final ClientServiceI clientService, final ClientRepositorySpring repository) {
        this.clientService = clientService;
        this.repository = repository;
    }

    @Test
    void createAdmin() {
        final Client admin = clientService.createAdmin();
        Assertions.assertThat(admin)
                .satisfies(client -> {
                    Assertions.assertThat(client.getId()).isNotNull();
                    Assertions.assertThat(client.getTasks()).isNull();
                })
                .extracting(
                        Client::getName,
                        Client::getEmail,
                        Client::getPassword,
                        Client::getStatus
                )
                .contains(
                        "Admin", "admin@gmail.com", "root", ClientStatus.ACTIVE
                );
    }

    @Test
    void findByEmailEmptyEmail() {
        final Client result = clientService.findByEmail("");
        Assertions.assertThat(result).isNull();
    }

    @Test
    void findByEmailNullEmail() {
        final Client result = clientService.findByEmail(null);
        Assertions.assertThat(result).isNull();
    }

    @Test
    void findByEmailWrongEmail() {
        clientService.createAdmin();
        final Client result = clientService.findByEmail("wrong@gmail.com");
        Assertions.assertThat(result).isNull();
    }

    @Test
    void findByEmail() {
        final Client admin = clientService.createAdmin();
        final Client result = clientService.findByEmail(admin.getEmail());
        Assertions.assertThat(result.getId()).isEqualTo(admin.getId());
    }

    @Test
    @Sql("/test-data.sql")
    void count() {
        Assertions.assertThat(clientService.countClients()).isEqualTo(25);
    }

    @Test
    @Sql("/test-data.sql")
    void countActive() {
        Assertions.assertThat(clientService.countClients(ClientStatus.ACTIVE)).isEqualTo(13);
    }

    @Test
    @Sql("/test-data.sql")
    void countInactive() {
        Assertions.assertThat(clientService.countClients(ClientStatus.INACTIVE)).isEqualTo(12);
    }
}