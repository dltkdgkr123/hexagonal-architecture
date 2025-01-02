package com.sh.hexagonal.application.port.in;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyCommand sendMoneyCommand);
}
