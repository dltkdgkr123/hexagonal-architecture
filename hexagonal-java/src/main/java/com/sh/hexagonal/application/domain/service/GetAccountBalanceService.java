package com.sh.hexagonal.application.domain.service;

import com.sh.hexagonal.application.domain.model.Money;
import com.sh.hexagonal.application.port.in.GetAccountBalanceUseCase;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalnce(@NotNull final GetAccountBalanceQuery query) {
        return loadAccountPort.loadAccount(query.accountId(), LocalDateTime.now())
            .calculateBalance();
    }
}
