package com.jkrotal.currency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "currency.client")
public class CurrencyClientCfg {
    private String url;
}