package com.sh.hexagonal.application.service;

import com.sh.hexagonal.application.port.out.AccountLock;
import com.sh.hexagonal.domain.model.Account.AccountId;
import org.springframework.stereotype.Component;

@Component
public class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(AccountId accountId) {
        // do nothing
    }
}
