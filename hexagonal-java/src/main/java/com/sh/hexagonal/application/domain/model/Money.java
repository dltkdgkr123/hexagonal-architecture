package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

@SuppressWarnings
    ({"checkstyle:MethodJavadoc",
        "checkstyle:Indentation",
        "checkstyle:MissingJavadocType"})
record Money(@NotNull BigInteger amount) {

    static Money ZERO = Money.of(0L);

    /* amount 자체가 long 범위보다 크면? */
    static Money of(final long amount) {
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

    boolean isGreaterThan(final Money money) {
        return amount.compareTo(money.amount) > 0;
    }

    boolean isGreaterThanOrEqualTo(final Money money) {
        return amount.compareTo(money.amount) >= 0;
    }

    Money add(final Money money) {
        return new Money(amount.add(money.amount));
    }

    Money subtract(final Money money) {
        return new Money(amount.subtract(money.amount));
    }

    static Money sum(final Money m1, final Money m2) {
        return m1.add(m2);
    }

    static Money differnce(final Money m1, final Money m2) {
        return m1.subtract(m2);
    }

    public Money negate() {
        return new Money(amount.negate());
    }
}
