package com.sh.hexagonal.application.port.out;

import com.sh.hexagonal.application.domain.model.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}
