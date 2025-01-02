package com.sh.hexagonal.application.service;

import com.sh.hexagonal.application.port.in.SendMoneyCommand;
import com.sh.hexagonal.application.port.in.SendMoneyUseCase;
import com.sh.hexagonal.application.port.out.AccountLock;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import com.sh.hexagonal.application.port.out.UpdateAccountStatePort;
import com.sh.hexagonal.common.annotation.UseCase;
import com.sh.hexagonal.domain.model.Account;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;

    private final UpdateAccountStatePort updateAccountStatePort;

    // no-operation account lock 사용 중 확인 (단일 스레드 환경 가정 및 아무 동작도 기대 x)
    private final AccountLock accountLock;

    // custom config 추가로 있던거 확인
    private final MoneyTransferProperties moneyTransferProperties;

    @Override
    public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {

        checkThreshold(sendMoneyCommand);

        LocalDateTime baselineDate = LocalDateTime.now();
//        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(sendMoneyCommand.getSourceAccountId(),
            baselineDate);
        Account targetAccount = loadAccountPort.loadAccount(sendMoneyCommand.getTargetAccountId(),
            baselineDate);

        Account.AccountId sourceAccountId = Optional.ofNullable(sourceAccount.getAccountId())
            .orElseThrow(AccountIdEmptyException::new);

        Account.AccountId targetAccountId = Optional.ofNullable(targetAccount.getAccountId())
            .orElseThrow(AccountIdEmptyException::new);

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(sendMoneyCommand.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(sendMoneyCommand.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);

        return true;
    }

    private void checkThreshold(SendMoneyCommand sendMoneyCommand) {
        if (sendMoneyCommand.getMoney()
            .isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
            throw new ThresholdExceededException(sendMoneyCommand.getMoney(),
                moneyTransferProperties.getMaximumTransferThreshold());
        }
    }
}
