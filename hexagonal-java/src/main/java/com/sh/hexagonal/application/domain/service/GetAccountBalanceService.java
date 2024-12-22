package com.sh.hexagonal.application.domain.service;

import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.domain.model.Money;
import com.sh.hexagonal.application.port.in.GetAccountBalanceQuery;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalnce(@NotNull final AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
            .calculateBalance();
    }
}
