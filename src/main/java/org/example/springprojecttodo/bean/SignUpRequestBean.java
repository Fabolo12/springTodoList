package org.example.springprojecttodo.bean;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record SignUpRequestBean(
        @NotNull String name,
        @NotNull String email,
        @NotNull String password,
        @NotNull @AssertTrue boolean acceptTerms
) {
    public SignUpRequestBean(
            @NotNull final String name,
            @NotNull final String email,
            @NotNull final String password,
            @NotNull @AssertTrue final boolean acceptTerms
    ) {
        this.name = name != null ? name.trim() : null;
        this.email = email != null ? email.trim() : null;
        this.password = password != null ? password.trim() : null;
        this.acceptTerms = acceptTerms;
    }
}
