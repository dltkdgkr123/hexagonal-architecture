package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityWindow {

    @NotNull
    private List<Activity> activities;

    LocalDateTime getStartTimestamp() {
        return activities.stream()
            .min(Comparator.comparing(Activity::getTimestamp))
            .orElseThrow(IllegalStateException::new)
            .getTimestamp();
    }

    LocalDateTime getEndTimestamp() {
        return activities.stream()
            .max(Comparator.comparing(Activity::getTimestamp))
            .orElseThrow(IllegalStateException::new)
            .getTimestamp();
    }

    Money calculateBalance(@NotNull final Account.AccountId accountId) {
        Money depositBalance = activities.stream()
            .filter(a -> a.getTargetAccountId().equals(accountId))
            .map(Activity::getMoney)
            .reduce(Money.ZERO, Money::add);

        Money withdrawalBalance = activities.stream()
            .filter(a -> a.getSourceAccountId().equals(accountId))
            .map(Activity::getMoney)
            .reduce(Money.ZERO, Money::add);

        return Money.sum(depositBalance, withdrawalBalance.negate());
    }


    public ActivityWindow(@NotNull final Activity... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

    public ActivityWindow(@NotNull final List<Activity> activities) {
        this.activities = activities;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(this.activities);
    }

    void addActivity(@NotNull final Activity activity) {
        activities.add(activity);
    }
}
