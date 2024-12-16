package com.sh.hexagonal.adapter.out.persistence;

import com.sh.hexagonal.application.domain.model.Account;
import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import com.sh.hexagonal.application.port.out.UpdateAccountStatePort;
import com.sh.hexagonal.common.annotation.PersistenceAdapter;
import java.time.LocalDateTime;

@PersistenceAdapter
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    // Mapper
    // Entity Repo
    // Spring Data Repo

    @Override
    public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
        return null;
    }

    @Override
    public void updateActivities(Account account) {

    }
}
