package com.sh.hexagonal.application.port.in;

import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.domain.model.Money;

public interface GetAccountBalanceUseCase {

    Money getAccountBalnce(GetAccountBalanceQuery query);

    record GetAccountBalanceQuery(AccountId accountId) {
    }

}
