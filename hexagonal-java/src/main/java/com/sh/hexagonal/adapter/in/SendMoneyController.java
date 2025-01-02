package com.sh.hexagonal.adapter.in;

import com.sh.hexagonal.domain.model.Account.AccountId;
import com.sh.hexagonal.domain.model.Money;
import com.sh.hexagonal.application.port.in.SendMoneyCommand;
import com.sh.hexagonal.application.port.in.SendMoneyUseCase;
import com.sh.hexagonal.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/account/send")
    void sendMoney(@RequestBody Long sourceAccountId,
        @RequestBody Long targetAccountId,
        @RequestBody Long amount) {

        sendMoneyUseCase.sendMoney(new SendMoneyCommand(
            new AccountId(sourceAccountId),
            new AccountId(targetAccountId),
            Money.of(amount)));
    }
}
