package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@SuppressWarnings
    ({"checkstyle:MethodJavadoc",
        "checkstyle:Indentation",
        "checkstyle:MissingJavadocType"})
record Account(AccountId accountId,
               @NotNull Money baselineBalance,
               @NotNull ActivityWindow activityWindow) {

    /* 아직 영속화 되지 않은 새로운 엔터티를 사용할 때 사용 */
    static Account withoutId(
        Money baselineBalance,
        ActivityWindow activityWindow) {

        return new Account(null, baselineBalance, activityWindow);
    }

    static Account withId(
        AccountId accountId,
        Money baselineBalance,
        ActivityWindow activityWindow) {

        return new Account(accountId, baselineBalance, activityWindow);
    }

    /* record getter랑 충돌 일으켜서 못씀 */
/*    public Optional<AccountId> accountId() {
        return Optional.ofNullable(accountId);
    }*/

    boolean hasAccountId() {
        return Optional.ofNullable(accountId).isPresent();
    }

    Money calculateBalance() {
        return Money.sum(baselineBalance, activityWindow.calculateBalance(accountId));
    }

    boolean withdraw(@NotNull final Money money, @NotNull final AccountId targetAccountId) {
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

    /* 입금 조건 : 아직은 항상 true */
    public boolean mayDeposit(@NotNull final Money money) {
        return true;
    }

    record AccountId(Long value) {

    }
}
