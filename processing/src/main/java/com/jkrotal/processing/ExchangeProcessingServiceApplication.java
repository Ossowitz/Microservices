package com.jkrotal.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExchangeProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeProcessingServiceApplication.class, args);
	}

}