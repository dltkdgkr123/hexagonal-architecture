package com.sh.hexagonal.application.port.in;

import com.sh.hexagonal.domain.model.Account.AccountId;
import com.sh.hexagonal.domain.model.Money;

// ooQuery -> UseCase보다 CQRS에 적합
public interface GetAccountBalanceQuery {

    Money getAccountBalnce(AccountId accountId);
}
