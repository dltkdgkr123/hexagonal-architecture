package com.sh.hexagonal.domain;

import com.sh.hexagonal.application.domain.model.Account;
import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.domain.model.ActivityWindow;
import com.sh.hexagonal.application.domain.model.Money;
import com.sh.hexagonal.common.AccountTestData;
import com.sh.hexagonal.common.ActivityTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AccountTest {

    /*
     * 액티비티 윈도우에 관한 동작 검증
     * - 올바른 연산 결과 반환에만 책임이 있음
     * - 유효성 판단 책임은 Account record에 위임
     * e.g.) 음수인 돈 입금, 마이너스 계좌 불가
     */
    @ParameterizedTest
    @CsvSource({
        "1, 300, 1, 1, 1, 1, 1, 1", // 자신과의 거래 => 변동 X
        "1, 600, 2, 2, 1, 3, 3, 1", // 타인의 입금 => 300 + 200 + 100
        "1, 0, 1, 1, 2, 1, 1, 3" // 타인에게 출금  => 300 - 200 - 100
    })
    void calculateBalance_ShouldReturnCorrectBalance(
        long testAccountId,
        long expectedBalance,

        long ownerAccountId1,
        long sourceAccountId1,
        long targetAccountId1,

        long ownerAccountId2,
        long sourceAccountId2,
        long targetAccountId2) {

        // Given
        AccountId accountId = new AccountId(testAccountId);

        Account account = AccountTestData.defaultAccount()
            .withAccountId(accountId)
            .withBaselineBalance(Money.of(300L))
            .withActivityWindow(new ActivityWindow(

                    ActivityTestData.defaultActivity()
                        .withOwnerAccountId(new AccountId(ownerAccountId1))
                        .withSourceAccountId(new AccountId(sourceAccountId1))
                        .withTargetAccountId(new AccountId(targetAccountId1))
                        .withMoney(Money.of(200L))
                        .build(),

                    ActivityTestData.defaultActivity()
                        .withOwnerAccountId(new AccountId(ownerAccountId2))
                        .withSourceAccountId(new AccountId(sourceAccountId2))
                        .withTargetAccountId(new AccountId(targetAccountId2))
                        .withMoney(Money.of(100L))
                        .build()

                )
            ).build();

        // When
        Money balance = account.calculateBalance();

        // Then
        Assertions.assertEquals(Money.of(expectedBalance), balance);
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
