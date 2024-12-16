package com.sh.hexagonal.domain;

import com.sh.hexagonal.application.domain.model.Account;
import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.domain.model.ActivityWindow;
import com.sh.hexagonal.application.domain.model.Money;
import com.sh.hexagonal.common.AccountTestData;
import com.sh.hexagonal.common.ActivityTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void calculateBalance() {
        AccountId accountId = new AccountId(1L);

        // Given

        // 1P has a balance of 100
        Account account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money.of(100L))
            .withActivityWindow(new ActivityWindow(

                    // 2P send 200 money to 1P
                    ActivityTestData.defaultActivity()
                        .withOwnerAccountId(new AccountId(2L))
                        .withSourceAccountId(new AccountId(2L))
                        .withTargetAccountId(new AccountId(1L))
                        .withMoney(Money.of(200L))
                        .build(),

                    // 1P send 300 money to 3P
                    ActivityTestData.defaultActivity()
                        .withOwnerAccountId(new AccountId(1L))
                        .withSourceAccountId(new AccountId(1L))
                        .withTargetAccountId(new AccountId(3L))
                        .withMoney(Money.of(300L))
                        .build()

                )
            ).build();

        // When
        Money balance = account.calculateBalance();

        // Then
        Assertions.assertEquals(Money.ZERO, balance);
    }

    @Test
    void withdraw() {

        // Given
        AccountId accountId = new AccountId(1L);

        Account account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money.of(100L))
            .withActivityWindow(new ActivityWindow())
            .build();

        // When
        boolean success = account.withdraw(Money.of(100L), new AccountId(3L));

        // Then
        Assertions.assertTrue(success);
    }
}
