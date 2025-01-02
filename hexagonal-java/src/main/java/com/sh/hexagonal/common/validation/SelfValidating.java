package com.sh.hexagonal.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

public abstract class SelfValidating<S> {

    private final Validator validator;

    public SelfValidating() {
        this.validator = jakarta.validation.Validation
            .buildDefaultValidatorFactory().getValidator();
    }

    public void validateSelf() {
        Set<ConstraintViolation<S>> violations = validator.validate((S) this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
