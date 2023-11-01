package com.jkrotal.processing.service;

import java.math.BigDecimal;

public interface ExchangeService {
    BigDecimal exchangeCurrency(String uuid, Long fromAccount, Long toAccount, BigDecimal amount);
}