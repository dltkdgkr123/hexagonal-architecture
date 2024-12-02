package com.sh.hexagonal.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

class Validation {

    private final static Validator validator =
        jakarta.validation.Validation
            .buildDefaultValidatorFactory().getValidator();

    public static <S> void validate(S subject) {
        Set<ConstraintViolation<S>> violations = validator.validate(subject);

        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder();

            for (ConstraintViolation<S> violation : violations) {
                message.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException(message.toString(), violations);
        }
    }
}
