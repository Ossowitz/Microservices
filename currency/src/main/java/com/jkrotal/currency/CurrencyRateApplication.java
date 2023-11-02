package com.jkrotal.currency;

import com.jkrotal.currency.config.CurrencyClientCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(CurrencyClientCfg.class)
public class CurrencyRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyRateApplication.class, args);
    }

}