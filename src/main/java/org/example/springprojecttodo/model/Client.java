package org.example.springprojecttodo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity
public class Client {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "client")
    private Set<Task> tasks;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;
}
