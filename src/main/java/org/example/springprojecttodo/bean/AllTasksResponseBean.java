package org.example.springprojecttodo.bean;

import java.time.LocalDate;
import java.util.UUID;

public record AllTasksResponseBean(
        UUID id,
        String title,
        boolean completed,
        LocalDate createdAt
) {
}

