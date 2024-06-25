package org.example.springprojecttodo.bean;

import jakarta.validation.constraints.NotNull;

public record LogInRequestBean(
        @NotNull String email,
        @NotNull String password
) {
    public LogInRequestBean(@NotNull final String email, @NotNull final String password) {
        this.email = email != null ? email.trim() : null;
        this.password = password != null ? password.trim() : null;
    }
}
