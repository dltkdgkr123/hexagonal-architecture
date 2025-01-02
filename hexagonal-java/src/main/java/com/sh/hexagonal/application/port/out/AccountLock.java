package com.sh.hexagonal.application.port.out;

import com.sh.hexagonal.domain.model.Account.AccountId;

public interface AccountLock {

    void lockAccount(AccountId accountId);

    void releaseAccount(AccountId accountId);

}
