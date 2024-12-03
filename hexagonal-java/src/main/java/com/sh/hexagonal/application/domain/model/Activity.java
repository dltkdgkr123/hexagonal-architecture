package com.sh.hexagonal.application.domain.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@SuppressWarnings
    ({"checkstyle:MethodJavadoc",
        "checkstyle:Indentation",
        "checkstyle:MissingJavadocType"})
record Activity(@NotNull Account.AccountId ownerAccountId,
                @NotNull Account.AccountId sourceAccountId,
                @NotNull Account.AccountId targetAccountId,
                @NotNull LocalDateTime timestamp,
                @NotNull Money money) {

    record ActivityId(@NotNull Long value) {

    }
}
