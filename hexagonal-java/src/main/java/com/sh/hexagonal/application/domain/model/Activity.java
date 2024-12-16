package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
@AllArgsConstructor
public class Activity {

    @Getter
    ActivityId activityId;

    @NotNull
    @Getter
    Account.AccountId ownerAccountId;

    @NotNull
    @Getter
    Account.AccountId sourceAccountId;

    @NotNull
    @Getter
    Account.AccountId targetAccountId;

    @NotNull
    @Getter
    LocalDateTime timestamp;

    @NotNull
    @Getter
    Money money;

    public record ActivityId(@NotNull Long value) {

    }

    public Activity(
        Account.AccountId ownerAccountId,
        Account.AccountId sourceAccountId,
        Account.AccountId targetAccountId,
        LocalDateTime timestamp,
        Money money) {

        this.activityId = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }
}
