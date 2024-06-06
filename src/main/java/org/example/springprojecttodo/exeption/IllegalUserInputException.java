package org.example.springprojecttodo.exeption;

public class IllegalUserInputException extends RuntimeException {
    public IllegalUserInputException() {
    }

    public IllegalUserInputException(final String message) {
        super(message);
    }
}
