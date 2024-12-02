package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

@SuppressWarnings
    ({"checkstyle:MethodJavadoc",
        "checkstyle:Indentation",
        "checkstyle:MissingJavadocType"})
public record Account(AccountId accountId,
                      @NotNull Money baselineBalance,
                      @NotNull ActivityWindow activityWindow) {

    /*
     *  아직 영속화 되지 않은 새로운 엔터티를 사용할 때 사용
     */
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

    // null + Optinal보다 나은 처리 방법이 있지 않을까?
    Optional<AccountId> getId() {
        return Optional.ofNullable(accountId);
    }

    /*
     * Identifier : Optinal, JPA와 호환을 위한 객체 선언
     */
    static record AccountId() {

        private static Long value;
    }

    static class ActivityWindow {

    }
}
