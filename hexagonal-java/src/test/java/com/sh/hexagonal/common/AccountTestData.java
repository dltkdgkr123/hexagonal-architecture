package com.sh.hexagonal.common;

import com.sh.hexagonal.application.domain.model.Account;
import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.domain.model.ActivityWindow;
import com.sh.hexagonal.application.domain.model.Money;

public class AccountTestData {

    public static AccountBuilder defaultAccount() {
        return new AccountBuilder()
            .withAccountId(new AccountId(3000L))
            .withBaselineBalance(Money.ZERO)
            .withActivityWindow(new ActivityWindow(
                ActivityTestData
                    .defaultActivity()
                    .build()));
    }

    public static class AccountBuilder {

        private AccountId accountId;
        private Money baselineBalance;
        private ActivityWindow activityWindow;

        public AccountBuilder withAccountId(AccountId accountId) {
            this.accountId = accountId;
            return this;
        }

        public AccountBuilder withBaselineBalance(Money baselineBalance) {
            this.baselineBalance = baselineBalance;
            return this;
        }

        public AccountBuilder withActivityWindow(ActivityWindow activityWindow) {
            this.activityWindow = activityWindow;
            return this;
        }

        public Account build() {
            return new Account(accountId, baselineBalance, activityWindow);
        }
    }
}
