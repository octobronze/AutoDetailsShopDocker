package com.example.AutoDetailsShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AutoDetailsShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoDetailsShopApplication.class, args);
	}

}
