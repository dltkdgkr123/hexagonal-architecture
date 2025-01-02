package com.sh.hexagonal.application.service;

import com.sh.hexagonal.application.port.in.GetAccountBalanceQuery;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import com.sh.hexagonal.common.annotation.UseCase;
import com.sh.hexagonal.domain.model.Account.AccountId;
import com.sh.hexagonal.domain.model.Money;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalnce(@NotNull final AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
            .calculateBalance();
    }
}
