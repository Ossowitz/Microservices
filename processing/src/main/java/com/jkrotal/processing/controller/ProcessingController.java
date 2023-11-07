package com.jkrotal.processing.controller;

import com.jkrotal.processing.dto.ExchangeMoneyDTO;
import com.jkrotal.processing.dto.NewAccountDTO;
import com.jkrotal.processing.dto.PutAccountDTO;
import com.jkrotal.processing.model.AccountEntity;
import com.jkrotal.processing.model.Operation;
import com.jkrotal.processing.service.AccountService;
import com.jkrotal.processing.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/processing")
public class ProcessingController {

    private final AccountService accountService;

    private final ExchangeService exchangeService;

    @PostMapping("/account")
    public AccountEntity createAccount(@RequestBody NewAccountDTO account) {
        return accountService.createNewAccount(account);
    }

    @PutMapping("/account/{id}")
    public AccountEntity putMoney(@PathVariable("id") Long accountId, @RequestBody PutAccountDTO data) {
        return accountService.addMoneyToAccount(data.getUuid(), accountId, null, Operation.PUT, data.getMoney());
    }

    @PutMapping("/exchange/{uid}")
    public BigDecimal exchangeMoney(@PathVariable("uid") String uid, @RequestBody ExchangeMoneyDTO data) {
        return exchangeService.exchangeCurrency(uid, data.getFromAccountId(), data.getToAccountId(), data.getMoney());
    }

    @GetMapping("/accounts")
    public List<AccountEntity> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}