package com.jkrotal.processing.service;

import com.jkrotal.processing.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.jkrotal.processing.model.Operation.*;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    private final AccountService accountService;

    private final CurrencyService currencyService;

    private static final String CURRENCY_RUB = "RUB";

    @Override
    public BigDecimal exchangeCurrency(String uuid, Long fromAccount, Long toAccount, BigDecimal amount) {

        AccountEntity sourceAccount = accountService.getAccountById(fromAccount);
        AccountEntity targetAccount = accountService.getAccountById(toAccount);

        LOGGER.info("Exchange operation {} from account {} to account {} started", uuid, fromAccount, toAccount);

        BigDecimal result;

        if (!CURRENCY_RUB.equals(sourceAccount.getCurrencyCode())
            && CURRENCY_RUB.equals(targetAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(sourceAccount.getCurrencyCode());
            result = exchangeWithMultiply(uuid, sourceAccount, targetAccount, rate, amount);
        } else if (CURRENCY_RUB.equals(sourceAccount.getCurrencyCode())
                   && !CURRENCY_RUB.equals(targetAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(targetAccount.getCurrencyCode());
            BigDecimal multiplier = new BigDecimal(1).divide(rate, 4, RoundingMode.HALF_DOWN);
            result = exchangeWithMultiply(uuid, sourceAccount, targetAccount, multiplier, amount);
        } else if (!CURRENCY_RUB.equals(sourceAccount.getCurrencyCode()) && !CURRENCY_RUB.equals(targetAccount.getCurrencyCode())) {
            BigDecimal rateFrom = currencyService.loadCurrencyRate(sourceAccount.getCurrencyCode());
            BigDecimal rateTo = currencyService.loadCurrencyRate(targetAccount.getCurrencyCode());
            result = exchangeThroughRub(uuid, sourceAccount, targetAccount, rateFrom, rateTo, amount);
        } else if (CURRENCY_RUB.equals(sourceAccount.getCurrencyCode()) && CURRENCY_RUB.equals(targetAccount.getCurrencyCode())) {
            result = simpleExchange(uuid, sourceAccount, targetAccount, amount);
        } else {
            throw new IllegalStateException("Unknown behavior");
        }

        LOGGER.info("Exchange operation {} from account {} to account {} completed", uuid, fromAccount, toAccount);

        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal simpleExchange(String uuid, AccountEntity sourceAccount, AccountEntity targetAccount, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, sourceAccount.getId(), targetAccount.getId(), EXCHANGE, amount.negate());
        accountService.addMoneyToAccount(uuid, targetAccount.getId(), sourceAccount.getId(), EXCHANGE, amount);
        return amount;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeWithMultiply(String uuid, AccountEntity sourceAccount, AccountEntity targetAccount,
                                           BigDecimal rate, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, sourceAccount.getId(), targetAccount.getId(), EXCHANGE, amount.negate());
        BigDecimal result = amount.multiply(rate);
        accountService.addMoneyToAccount(uuid, targetAccount.getId(), sourceAccount.getId(), EXCHANGE, result);
        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeThroughRub(String uuid, AccountEntity sourceAccount, AccountEntity targetAccount,
                                         BigDecimal rateFrom, BigDecimal rateTo, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, sourceAccount.getId(), targetAccount.getId(), EXCHANGE, amount.negate());
        BigDecimal rub = amount.multiply(rateFrom);
        BigDecimal result = rub.divide(rateTo, 4, RoundingMode.HALF_DOWN);
        accountService.addMoneyToAccount(uuid, targetAccount.getId(), sourceAccount.getId(), EXCHANGE, result);
        return result;
    }
}