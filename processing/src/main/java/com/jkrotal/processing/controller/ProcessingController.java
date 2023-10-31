package com.jkrotal.processing.controller;

import com.jkrotal.processing.dto.NewAccountDTO;
import com.jkrotal.processing.dto.PutAccountDTO;
import com.jkrotal.processing.model.AccountEntity;
import com.jkrotal.processing.model.Operation;
import com.jkrotal.processing.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/processing")
public class ProcessingController {

    private final AccountService accountService;

    @PostMapping("/account")
    public AccountEntity createAccount(@RequestBody NewAccountDTO account) {
        return accountService.createNewAccount(account);
    }

    @PutMapping("/account/{id}")
    public AccountEntity putMoney(@PathVariable("id") Long accountId, @RequestBody PutAccountDTO data) {
        return accountService.addMoneyToAccount(data.getUuid(), accountId, null, Operation.PUT, data.getMoney());
    }

}