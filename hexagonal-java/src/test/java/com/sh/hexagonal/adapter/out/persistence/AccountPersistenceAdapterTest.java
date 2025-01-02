package com.sh.hexagonal.adapter.out.persistence;

import com.sh.hexagonal.application.domain.model.Account;
import com.sh.hexagonal.application.domain.model.Account.AccountId;
import com.sh.hexagonal.application.domain.model.Activity.ActivityId;
import com.sh.hexagonal.application.domain.model.ActivityWindow;
import com.sh.hexagonal.application.domain.model.Money;
import com.sh.hexagonal.common.AccountTestData;
import com.sh.hexagonal.common.ActivityTestData;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
class AccountPersistenceAdapterTest {

    @Autowired
    private AccountPersistenceAdapter adapterUnderTest;
    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Maps the account entity and activities to the account domain.
     *
     * <p>
     * Activities before the baseline date are aggregated to compute the balance at that time.
     * </p>
     *
     * <p>
     * Activities after the baseline date are added to the activity window.
     * </p>
     *
     * {@throws EntityNotFoundException}
     */
    @Test
    void loadsAccount() {
        Account account = adapterUnderTest.loadAccount(new AccountId(1L),
            LocalDateTime.of(2018, 8, 10, 0, 0));

        Assertions.assertEquals(account.getActivityWindow().getActivities().size(), 2);
        Assertions.assertEquals(account.calculateBalance(), Money.of(500L));
    }
}
