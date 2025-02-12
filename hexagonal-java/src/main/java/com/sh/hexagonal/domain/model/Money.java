package com.sh.hexagonal.domain.model;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.Value;

@Value
public class Money {

    @NotNull
    BigInteger amount;

    public static Money ZERO = Money.of(0L);

    /** amount 자체가 long 범위보다 크면? */
    public static Money of(final long amount) {
        return new Money(BigInteger.valueOf(amount));
    }

    public boolean isPositive() {
        return amount.compareTo(BigInteger.ZERO) > 0;
    }

    public boolean isPositiveOrZero() {
        return amount.compareTo(BigInteger.ZERO) >= 0;
    }

    public boolean isNegative() {
        return amount.compareTo(BigInteger.ZERO) < 0;
    }

    public boolean isGreaterThan(final Money money) {
        return amount.compareTo(money.amount) > 0;
    }

    public boolean isGreaterThanOrEqualTo(final Money money) {
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

    public static Money differnce(final Money m1, final Money m2) {
        return m1.subtract(m2);
    }

    public Money negate() {
        return new Money(amount.negate());
    }
}
