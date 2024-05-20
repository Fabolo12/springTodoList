package org.example.springprojecttodo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Task {
    private UUID id;

    private String title;

    private String description;

    private boolean completed;

    private LocalDate createdAt;
}
