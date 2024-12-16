package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Account {

    @Getter
    private final AccountId accountId;

    @Getter
    @NotNull
    private final Money baselineBalance;

    @Getter
    @NotNull
    private final ActivityWindow activityWindow;

    public record AccountId(Long value) {

    }

    public static Account withoutId(
        Money baselineBalance,
        ActivityWindow activityWindow) {

        return new Account(null, baselineBalance, activityWindow);
    }

    public static Account withId(
        AccountId accountId,
        Money baselineBalance,
        ActivityWindow activityWindow) {

        return new Account(accountId, baselineBalance, activityWindow);
    }

    boolean hasAccountId() {
        return Optional.ofNullable(accountId).isPresent();
    }

    public Money calculateBalance() {
        return Money.sum(baselineBalance, activityWindow.calculateBalance(accountId));
    }

    public boolean withdraw(@NotNull final Money money, @NotNull final AccountId targetAccountId) {
        if (!mayWithdraw(money)) {
            return false;
        }

        activityWindow
            .addActivity(new Activity(
                accountId, // ownerAccountId
                accountId, // sourceAccountId
                targetAccountId,
                LocalDateTime.now(),
                money));

        return true;
    }

    boolean mayWithdraw(@NotNull final Money money) {
        return Money.sum(baselineBalance, money.negate())
            .isPositiveOrZero();
    }

    public boolean deposit(@NotNull final Money money, @NotNull final AccountId sourceAccountId) {
        if (!mayDeposit(money)) {
            return false;
        }

        activityWindow
            .addActivity(new Activity(
                accountId, // ownerAccountId
                sourceAccountId,
                accountId, // targetAccountId
                LocalDateTime.now(),
                money));

        return true;
    }

    /* 예금 조건 : 아직은 항상 true */
    boolean mayDeposit(@NotNull final Money money) {
        return true;
    }
}
