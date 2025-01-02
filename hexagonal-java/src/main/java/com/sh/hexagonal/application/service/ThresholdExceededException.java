package com.sh.hexagonal.application.service;

import com.sh.hexagonal.domain.model.Money;

public class ThresholdExceededException extends RuntimeException {

    public ThresholdExceededException(Money actual, Money threshold) {
        super(String.format(
            "Maximum threshold for transferring money exceeded: tried to transfer %s but threshold is %s!",
            actual, threshold));
    }
}
