package com.jkrotal.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PutAccountDTO {

    @JsonAlias("uid")
    private String uuid;

    @JsonAlias("account")
    private Long accountId;

    @JsonAlias("amount")
    private BigDecimal money;
}