package com.sh.hexagonal.application.port.out;

import com.sh.hexagonal.domain.model.Account;
import com.sh.hexagonal.domain.model.Account.AccountId;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(@NotNull final AccountId accountId,
        @NotNull final LocalDateTime baselineDate);
}
