package com.sh.hexagonal.application.domain.service;

import com.sh.hexagonal.application.port.in.SendMoneyCommand;
import com.sh.hexagonal.application.port.in.SendMoneyUseCase;

public class SendMoneyService implements SendMoneyUseCase {



    @Override
    public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {
        return false;
    }
}
