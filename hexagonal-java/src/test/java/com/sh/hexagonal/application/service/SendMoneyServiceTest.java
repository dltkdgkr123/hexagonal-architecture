package com.sh.hexagonal.application.service;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.sh.hexagonal.application.port.in.SendMoneyCommand;
import com.sh.hexagonal.application.port.out.AccountLock;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import com.sh.hexagonal.application.port.out.UpdateAccountStatePort;
import com.sh.hexagonal.domain.model.Account;
import com.sh.hexagonal.domain.model.Account.AccountId;
import com.sh.hexagonal.domain.model.Money;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SendMoneyServiceTest {

    private final LoadAccountPort loadAccountPort = mock(LoadAccountPort.class);

    private final UpdateAccountStatePort updateAccountStatePort = mock(
        UpdateAccountStatePort.class);

    private final AccountLock accountLock = mock(AccountLock.class);

    private final SendMoneyService sendMoneyService = mock(SendMoneyService.class);

    @Test
    void givenWithdrawalFails_thenOnlySourceAccountIsLockedAndReleased() {

        AccountId sourceAccountId = new AccountId(41L);
        Account sourceAccount = givenAnAccountWithId(sourceAccountId);

        AccountId targetAccountId = new AccountId(42L);
        Account targetAccount = givenAnAccountWithId(targetAccountId);

        given(sourceAccount.withdraw(any(Money.class), any(AccountId.class)))
            .willReturn(false);
        given(targetAccount.deposit(any(Money.class), any(AccountId.class)))
            .willReturn(true);

        SendMoneyCommand sendMoneyCommand = new SendMoneyCommand(
            sourceAccountId,
            targetAccountId,
            Money.of(300L));

        boolean success = sendMoneyService.sendMoney(sendMoneyCommand);

        Assertions.assertFalse(success);

        then(accountLock).should().lockAccount(eq(sourceAccountId));
        then(accountLock).should().releaseAccount(eq(sourceAccountId));
        then(accountLock).should(times(0)).lockAccount(eq(targetAccountId));
    }

    private Account givenAnAccountWithId(AccountId accountId) {
        Account account = mock(Account.class);

        given(account.getAccountId())
            .willReturn(accountId);

        given(loadAccountPort.loadAccount(eq(account.getAccountId()), any(LocalDateTime.class)))
            .willReturn(account);

        return account;
    }
}
