package com.sh.hexagonal.adapter.out.persistence;

import com.sh.hexagonal.application.domain.model.Account;
import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import com.sh.hexagonal.application.port.out.UpdateAccountStatePort;
import com.sh.hexagonal.common.annotation.PersistenceAdapter;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@PersistenceAdapter
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    AccountMapper accountMapper;
    ActivityRepository activityRepository;
    SpringDataAccountRepository accountRepository;

    @Override
    public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {

        AccountJpaEntity account =
            accountRepository.findById(accountId.value())
                .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
            accountId.value(),
            baselineDate);

        Long depositBalance = orZero(
            activityRepository.getDepositBalanceUntil(accountId.value(), baselineDate));

        Long withdrawalBalance = orZero(
            activityRepository.getWithdrawalBalanceUntil(accountId.value(), baselineDate));

        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        );
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public void updateActivities(Account account) {

    }
}
