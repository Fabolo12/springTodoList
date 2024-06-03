package org.example.springprojecttodo.exeption;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound() {
    }

    public EntityNotFound(final String message) {
        super(message);
    }
}
