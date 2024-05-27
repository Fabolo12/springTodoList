package org.example.springprojecttodo.service.creator;

import org.example.springprojecttodo.model.Client;
import org.example.springprojecttodo.model.ClientStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClientCreator {

    @Value("${spring.profiles.active}")
    private String profile;

    private final List<String> realizationsDemandIdGeneration = List.of("map", "jdbc");

    public Client createClient() {
        final UUID id = realizationsDemandIdGeneration.contains(profile) ? UUID.randomUUID() : null;

        return Client.builder()
                .id(id)
                .email("admin@gmail.com")
                .password("root")
                .name("Admin")
                .status(ClientStatus.ACTIVE)
                .build();
    }
}
