package org.example.springprojecttodo.shared;

import org.example.springprojecttodo.exeption.IllegalUserInputException;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class MyExceptionUtils {

    private MyExceptionUtils() {
    }

    // TODO write aspect instead of this method
    public static void checkValidationResult(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new IllegalUserInputException(errorMessage);
        }
    }
}
