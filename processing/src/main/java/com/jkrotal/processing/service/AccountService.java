package com.jkrotal.processing.service;

import com.jkrotal.processing.dto.NewAccountDTO;
import com.jkrotal.processing.model.AccountEntity;
import com.jkrotal.processing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
}