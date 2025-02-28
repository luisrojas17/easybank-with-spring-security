package com.easybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
// This annotation is optional
@EnableWebSecurity
public class EasyBankApplication {

	public static void main(String[] args) {

		SpringApplication.run(EasyBankApplication.class, args);
	}

}
