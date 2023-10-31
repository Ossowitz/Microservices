package com.jkrotal.processing.service;

import com.jkrotal.processing.dto.NewAccountDTO;
import com.jkrotal.processing.exception.AccountNotFoundException;
import com.jkrotal.processing.model.AccountEntity;
import com.jkrotal.processing.model.Operation;
import com.jkrotal.processing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountEntity createNewAccount(NewAccountDTO dto) {
        AccountEntity account = AccountEntity.builder()
                .currencyCode(dto.getCurrencyCode())
                .userId(dto.getUserId())
                .balance(new BigDecimal(0))
                .build();

        return accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public AccountEntity addMoneyToAccount(String uid, Long accountId, Long targetAccount,
                                           Operation operation, BigDecimal money) {
        Optional<AccountEntity> account = accountRepository.findById(accountId);
        return account.map(it -> {
            BigDecimal balance = it.getBalance().add(money);
            it.setBalance(balance);

            return accountRepository.save(it);
        }).orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}