package org.example.springprojecttodo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {
    private UUID id;

    private String name;

    private String email;

    private String password;

    private Set<Task> tasks;

    private ClientStatus status;
}
