package com.jkrotal.processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private static final String CURRENCY_RATE_URL = "/currency/rate/{code}";

    private final RestTemplate restClient;

    @Value("${service.currency.url}")
    private String currencyUrl;

    public BigDecimal loadCurrencyRate(String code) {
        return restClient.getForObject(currencyUrl + CURRENCY_RATE_URL,
                BigDecimal.class, code);
    }
}