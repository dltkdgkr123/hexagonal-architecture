package com.sh.hexagonal.common;

import com.sh.hexagonal.domain.model.Account;
import com.sh.hexagonal.domain.model.Account.AccountId;
import com.sh.hexagonal.domain.model.Activity;
import com.sh.hexagonal.domain.model.Activity.ActivityId;
import com.sh.hexagonal.domain.model.Money;
import java.time.LocalDateTime;

public class ActivityTestData {

    public static ActivityBuilder defaultActivity() {
        return new ActivityBuilder()
            // .withActivityId()
            .withOwnerAccountId(new AccountId(3000L))
            .withSourceAccountId(new AccountId(3000L))
            .withTargetAccountId(new AccountId(3000L))
            .withTimestamp(LocalDateTime.now())
            .withMoney(Money.ZERO);
    }

    public static class ActivityBuilder {

        ActivityId activityId;
        Account.AccountId ownerAccountId;
        Account.AccountId sourceAccountId;
        Account.AccountId targetAccountId;
        LocalDateTime timestamp;
        Money money;

        public ActivityBuilder withActivityId(ActivityId activityId) {
            this.activityId = activityId;
            return this;
        }

        public ActivityBuilder withOwnerAccountId(AccountId ownerAccountId) {
            this.ownerAccountId = ownerAccountId;
            return this;
        }

        public ActivityBuilder withSourceAccountId(AccountId sourceAccountId) {
            this.sourceAccountId = sourceAccountId;
            return this;
        }

        public ActivityBuilder withTargetAccountId(AccountId targetAccountId) {
            this.targetAccountId = targetAccountId;
            return this;
        }

        public ActivityBuilder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ActivityBuilder withMoney(Money money) {
            this.money = money;
            return this;
        }

        public Activity build() {
            return new Activity(
                ownerAccountId,
                sourceAccountId,
                targetAccountId,
                timestamp,
                money);
        }
    }
}
