package org.example.springprojecttodo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String description;

    private boolean completed;

    private LocalDate createdAt;

    @ManyToOne
    private Client client;
}
