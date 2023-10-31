package com.jkrotal.processing.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long accountId) {
        super("Account with ID " + accountId + " not found");
    }
}