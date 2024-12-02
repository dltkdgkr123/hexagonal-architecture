package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

@SuppressWarnings
    ({"checkstyle:MethodJavadoc",
        "checkstyle:Indentation",
        "checkstyle:MissingJavadocType"})
public record Money(@NotNull BigInteger amount) {

    static Money of(long amount) {
        return new Money(BigInteger.valueOf(amount));
    }

    boolean isPositive() {
        return amount.compareTo(BigInteger.ZERO) > 0;
    }

    boolean isPositiveOrZero() {
        return amount.compareTo(BigInteger.ZERO) >= 0;
    }

    boolean isNegative() {
        return amount.compareTo(BigInteger.ZERO) < 0;
    }

    boolean isGreaterThan(Money money) {
        return amount.compareTo(money.amount) > 0;
    }

    boolean isGreaterThanOrEqualTo(Money money) {
        return amount.compareTo(money.amount) >= 0;
    }

    Money add(Money money) {
        return new Money(amount.add(money.amount));
    }

    Money subtract(Money money) {
        return new Money(amount.subtract(money.amount));
    }
}