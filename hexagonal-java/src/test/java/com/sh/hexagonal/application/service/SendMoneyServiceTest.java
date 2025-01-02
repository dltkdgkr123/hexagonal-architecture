package com.sh.hexagonal.application.service;

import com.sh.hexagonal.application.port.out.LoadAccountPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SendMoneyServiceTest {

    private final LoadAccountPort loadAccountPort = Mockito.mock(LoadAccountPort.class);

    @Test
    void contextLoads() {

    }
}
