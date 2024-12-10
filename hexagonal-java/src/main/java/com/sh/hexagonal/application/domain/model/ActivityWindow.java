package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings
    ({"checkstyle:MethodJavadoc",
        "checkstyle:Indentation",
        "checkstyle:MissingJavadocType"})
public record ActivityWindow(@NotNull List<Activity> activities) {

    LocalDateTime getStartTimestamp() {
        return activities.stream()
            .min(Comparator.comparing(Activity::timestamp))
            .orElseThrow(IllegalStateException::new)
            .timestamp();
    }

    LocalDateTime getEndTimestamp() {
        return activities.stream()
            .max(Comparator.comparing(Activity::timestamp))
            .orElseThrow(IllegalStateException::new)
            .timestamp();
    }

    Money calculateBalance(@NotNull final Account.AccountId accountId) {
        Money depositBalance = activities.stream()
            .filter(a -> a.targetAccountId().equals(accountId))
            .map(Activity::money)
            .reduce(Money.ZERO, Money::add);

        Money withdrawalBalance = activities.stream()
            .filter(a -> a.sourceAccountId().equals(accountId))
            .map(Activity::money)
            .reduce(Money.ZERO, Money::add);

        return Money.sum(depositBalance, withdrawalBalance.negate());
    }


    /* Non-canonical record constructor must delegate to another constructor */
    public ActivityWindow(@NotNull final Activity... activities) {
        this(List.of(activities));
    }

    /*
     * 목적 :
     * 1. activities는 Activity를 추가할 수 있는 mutable 객체여야 한다.
     * 2. getter는 immutable 객체를 반환해야 한다.
     * 3. record에서 setter는 제공하지 않지만, getter를 통해 레퍼런스 접근 및 수정이 가능하기에 이를 막아야 한다.
     *
     * @throw UnsupportedOperationException - activities()를 이용한 접근 후 수정 시
     */
    @Override
    public List<Activity> activities() {
        return Collections.unmodifiableList(activities);
    }

    void addActivity(@NotNull final Activity activity) {
        activities.add(activity);
    }
}
