package org.example.springprojecttodo.bean;

import java.time.LocalDate;
import java.util.UUID;

public record TasksResponseBean(
        UUID id,
        String title,
        boolean completed,
        LocalDate createdAt
) {
}

