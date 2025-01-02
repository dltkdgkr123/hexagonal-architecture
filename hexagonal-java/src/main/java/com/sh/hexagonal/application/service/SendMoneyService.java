package com.sh.hexagonal.application.service;

import com.sh.hexagonal.application.port.in.SendMoneyCommand;
import com.sh.hexagonal.application.port.in.SendMoneyUseCase;
import com.sh.hexagonal.application.port.out.LoadAccountPort;
import com.sh.hexagonal.common.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {
        return false;
    }
}
