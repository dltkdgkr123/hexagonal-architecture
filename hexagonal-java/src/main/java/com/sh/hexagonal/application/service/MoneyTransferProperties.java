package com.sh.hexagonal.application.service;

import com.sh.hexagonal.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferProperties {

    private final Money maximumTransferThreshold = Money.of(1_000_000L);
}
