package com.lorenzo_rodrigues.desafio_backend_picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioBackendPicpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendPicpayApplication.class, args);
	}
}
