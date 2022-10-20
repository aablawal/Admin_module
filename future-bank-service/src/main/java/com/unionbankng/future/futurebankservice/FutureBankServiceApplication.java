package com.unionbankng.future.futurebankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@SpringBootApplication
public class FutureBankServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FutureBankServiceApplication.class, args);
	}

	@Bean(name = "initiateKycExecutor")
	public Executor initiateKycExecutor(){
		return Executors.newFixedThreadPool(10);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
