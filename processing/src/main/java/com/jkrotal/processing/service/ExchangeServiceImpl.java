package com.jkrotal.processing.service;

import com.jkrotal.processing.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

        /**
         * If the sender's currency is not ruble (CURRENCY_RUB), and the recipient's currency is ruble,
         *         we load the conversion rate and perform the exchange taking into account the coefficient
         */
        if (!CURRENCY_RUB.equals(sourceAccount.getCurrencyCode())
            && CURRENCY_RUB.equals(targetAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(sourceAccount.getCurrencyCode());
            result = exchangeWithMultiply(uuid, sourceAccount, targetAccount, rate, amount);
        }

        LOGGER.info("Exchange operation {} from account {} to account {} completed", uuid, fromAccount, toAccount);

        return null;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal exchangeWithMultiply(String uuid, AccountEntity sourceAccount, AccountEntity targetAccount,
                                           BigDecimal rate, BigDecimal amount) {
        accountService.addMoneyToAccount(uuid, sourceAccount.getId(), targetAccount.getId(), EXCHANGE, amount.negate());
        BigDecimal result = amount.multiply(rate);
        accountService.addMoneyToAccount(uuid, targetAccount.getId(), sourceAccount.getId(), EXCHANGE, result);
        return result;
    }
}