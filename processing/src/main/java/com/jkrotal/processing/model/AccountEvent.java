package com.jkrotal.processing.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountEvent {

    private String uuid;

    private Long userId;

    private Long accountId;

    private Long fromAccount;

    private String currency;

    private Operation operation;

    private BigDecimal amount;

    private Date created;
}