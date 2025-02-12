package com.sh.hexagonal.adapter.out.persistence;

import com.sh.hexagonal.domain.model.Account;
import com.sh.hexagonal.domain.model.Activity;
import com.sh.hexagonal.domain.model.Activity.ActivityId;
import com.sh.hexagonal.domain.model.ActivityWindow;
import com.sh.hexagonal.domain.model.Money;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    /**
     * AccountJpaEntity -> Account 변환
     */
    Account mapToDomainEntity(
        AccountJpaEntity account,
        List<ActivityJpaEntity> activities,
        Long depositBalance,
        Long withdrawalBalance) {

        Money baselineBalance = Money.differnce(
            Money.of(depositBalance),
            Money.of(withdrawalBalance));

        return Account.withId(
            new Account.AccountId(account.getId()),
            baselineBalance,
            mapToActivityWindow(activities));
    }

    /**
     * 동작 순서 1. ActivityJpaEntity -> Activity 맵핑 2. List<Activity>에 Activity 들을 추가 3. List<Activity>
     * -> ActivityWindow 변환
     */
    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {

        List<Activity> mappedActivities = new ArrayList<>();

        for (ActivityJpaEntity activity : activities) {
            mappedActivities.add(
                new Activity(
                    new ActivityId(activity.getId()),
                    new Account.AccountId(activity.getOwnerAccountId()),
                    new Account.AccountId(activity.getSourceAccountId()),
                    new Account.AccountId(activity.getTargetAccountId()),
                    activity.getTimestamp(),
                    Money.of(activity.getAmount())
                )
            );
        }

        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
            activity.getActivityId() == null ? null : activity.getActivityId().value(),
            activity.getOwnerAccountId().value(),
            activity.getSourceAccountId().value(),
            activity.getTargetAccountId().value(),
            activity.getMoney().getAmount().longValue(),
            activity.getTimestamp()
        );
    }
}
